/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah .
 */
package com.panemu.tiwulfx.control.skin;

import com.panemu.tiwulfx.control.TypeAheadField;
import com.sun.javafx.scene.ParentHelper;
import com.sun.javafx.scene.control.FakeFocusTextField;
import com.sun.javafx.scene.control.behavior.TextInputControlBehavior;
import com.sun.javafx.scene.traversal.Algorithm;
import com.sun.javafx.scene.traversal.Direction;
import com.sun.javafx.scene.traversal.ParentTraversalEngine;
import com.sun.javafx.scene.traversal.TraversalContext;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Skinnable;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author Amrullah 
 */
public class TypeAheadFieldSkin<T> extends SkinBase<TypeAheadField<T>> {

	private TextField textField;
	private Button button;
	private PopupControl popup;
	private boolean detectTextChanged = true;
	private Timer waitTimer;
	private LoaderTimerTask loaderTimerTask;
	private TypeAheadField<T> typeAheadField;
	private Logger logger = Logger.getLogger(TypeAheadFieldSkin.class.getName());
	/**
	 * flag to
	 */
	public boolean needValidation = true;

	public TypeAheadFieldSkin(TypeAheadField<T> control) {
		super(control);
		this.typeAheadField = control;
		// move focus in to the textfield
		typeAheadField.focusedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean hasFocus) -> {
			((FakeFocusTextField) textField).setFakeFocus(hasFocus);
		});

		ParentHelper.setTraversalEngine(typeAheadField,
				  new ParentTraversalEngine(typeAheadField, new Algorithm() {

					  @Override
					  public Node select(Node owner, Direction dir, TraversalContext context) {
						  return null;
					  }

					  @Override
					  public Node selectFirst(TraversalContext context) {
						  return null;
					  }

					  @Override
					  public Node selectLast(TraversalContext context) {
						  return null;
					  }
				  }));
		initialize();

		typeAheadField.focusedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean hasFocus) -> {
			if (!hasFocus) {
				validate();
			}
		});

		textField.addEventFilter(KeyEvent.ANY, (KeyEvent ke) -> {
			if (ke.getCode() == KeyCode.DOWN) {
				//prevent moving caret position to the end
				ke.consume();
			}
		});
		
		typeAheadField.addEventFilter(InputEvent.ANY, new EventHandler<InputEvent>() {
			@Override
			public void handle(InputEvent t) {
				if (textField == null) {
					return;
				}
				
				// When the user hits the enter or F4 keys, we respond before 
				// ever giving the event to the TextField.
				if (t instanceof KeyEvent) {
					KeyEvent ke = (KeyEvent) t;
					if (ke.getTarget().equals(textField)) return;
					if (ke.getCode() == KeyCode.DOWN && ke.getEventType() == KeyEvent.KEY_RELEASED) {
						if (!typeAheadField.isShowingSuggestion()) {
							typeAheadField.showSuggestion();
						}
						t.consume();
						return;
					} else if ((ke.getCode() == KeyCode.F10 || ke.getCode() == KeyCode.ESCAPE || ke.getCode() == KeyCode.ENTER)
							  && !ke.isControlDown()) {

						// RT-23275: The TextField fires F10 and ESCAPE key events
						// up to the parent, which are then fired back at the 
						// TextField, and this ends up in an infinite loop until
						// the stack overflows. So, here we consume these two
						// events and stop them from going any further.
						t.consume();
						return;
					} else {
                    // Fix for the regression noted in a comment in RT-29885.
                    // This forwards the event down into the TextField when
                    // the key event is actually received by the ComboBox.
                    textField.fireEvent(ke.copyFor(textField, textField));
                    ke.consume();
					}
				}
			}
		});

		textField.promptTextProperty().bind(typeAheadField.promptTextProperty());

		getSkinnable().requestLayout();

	}

	public void hideSuggestion() {
		if (popup != null && popup.isShowing()) {
			popup.hide();
		}
	}

	private PopupControl getPopup() {
		if (popup == null) {
			createPopup();
		}
		return popup;
	}

	/**
	 * Get the reference to the underlying textfield. This method is used by TypeAheadTableCell.
	 *
	 * @return TextField
	 */
	public TextField getTextField() {
		return textField;
	}

	private void createPopup() {
		popup = new PopupControl() {
			{
				setSkin(new Skin() {
					@Override
					public Skinnable getSkinnable() {
						return TypeAheadFieldSkin.this.typeAheadField;
					}

					@Override
					public Node getNode() {
						return listView;
					}

					@Override
					public void dispose() {
					}
				});
			}
		};
		popup.setAutoHide(true);
		popup.setOnHidden((e) -> typeAheadField.hideSuggestion());
		popup.setAutoFix(true);
		popup.setHideOnEscape(true);
		popup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				typeAheadField.hideSuggestion();
			}
		});

		listView.setCellFactory(new Callback() {
			@Override
			public Object call(Object p) {
				return new PropertyListCell();
			}
		});

		/**
		 * Taken from {@link com.sun.javafx.scene.control.skin.ComboBoxListViewSkin}
		 */
		listView.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				// RT-18672: Without checking if the user is clicking in the 
				// scrollbar area of the ListView, the comboBox will hide. Therefore,
				// we add the check below to prevent this from happening.
				EventTarget target = t.getTarget();
				if (target instanceof Parent) {
					List<String> s = ((Parent) target).getStyleClass();
					if (s.contains("thumb")
							  || s.contains("track")
							  || s.contains("decrement-arrow")
							  || s.contains("increment-arrow")) {
						return;
					}
				}
				needValidation = false;
				typeAheadField.setValue(listView.getSelectionModel().getSelectedItem());
				typeAheadField.hideSuggestion();
			}
		});

		listView.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					needValidation = false;
					if (listView.getSelectionModel().getSelectedItem() != null) {
						/**
						 * By default, select the first item if none is selected
						 */
						if (listView.getSelectionModel().getSelectedItem() == typeAheadField.getValue()) {
							/**
							 * Update the textfield. User may have changed it.
							 */
							updateTextField();
						} else {
							typeAheadField.setValue(listView.getSelectionModel().getSelectedItem());
							/**
							 * The textfield will be updated by value change listener
							 */
						}

					} else if (!listView.getItems().isEmpty()) {
						T defaultItem = listView.getItems().get(0);
						if (defaultItem == typeAheadField.getValue()) {
							/**
							 * Update the textfield. User may have changed it.
							 */
							updateTextField();
						} else {
							typeAheadField.setValue(defaultItem);
							/**
							 * The textfield will be updated by value change listener
							 */
						}
					}

					typeAheadField.hideSuggestion();
				} else if (t.getCode() == KeyCode.ESCAPE) {
					typeAheadField.hideSuggestion();
				} else if (t.getCode() == KeyCode.RIGHT) {
					textField.positionCaret(textField.getCaretPosition() + 1);
					Platform.runLater(() -> refreshList());
					t.consume();
				} else if (t.getCode() == KeyCode.LEFT) {
					textField.positionCaret(textField.getCaretPosition() - 1);
					Platform.runLater(() -> refreshList());
					t.consume();
				} else if (t.getCode() == KeyCode.TAB) {
					if (!listView.getItems().isEmpty()) {
						T defaultItem = listView.getSelectionModel().getSelectedItem();
						defaultItem = defaultItem == null ? listView.getItems().get(0) : defaultItem;
						if (defaultItem == typeAheadField.getValue()) {
							/**
							 * Update the textfield. User may have changed it.
							 */
							updateTextField();
						} else {
							typeAheadField.setValue(defaultItem);
							/**
							 * The textfield will be updated by value change listener
							 */
						}
					}
					typeAheadField.hideSuggestion();
					Event.fireEvent(textField, t);
				}
			}
		});

	}

	private void initialize() {
		textField = new FakeFocusTextField();
		textField.setFocusTraversable(false);
		textField.getProperties().put(TextInputControlBehavior.DISABLE_FORWARD_TO_PARENT, true);
		button = new Button();
		button.setFocusTraversable(false);
		StackPane arrow = new StackPane();
		arrow.setFocusTraversable(false);
		arrow.getStyleClass().setAll("arrow");
		arrow.setMaxWidth(USE_PREF_SIZE);
		arrow.setMaxHeight(USE_PREF_SIZE);

		button.setGraphic(arrow);
		StackPane.setAlignment(textField, Pos.CENTER_LEFT);
		StackPane.setAlignment(button, Pos.CENTER_RIGHT);
		this.getChildren().addAll(textField, button);
		button.setOnAction((ActionEvent t) -> {
			if (!typeAheadField.isFocused()) {
				/**
				 * Need to make this control become focused. Otherwise changing value in LookupColumn while the LookuField cell editor is not focused before, won't trigger commitEdit()
				 */
				typeAheadField.requestFocus();
			}
			typeAheadField.showSuggestion();
		});
		updateTextField();
		typeAheadField.valueProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				updateTextField();
			}
		});

		typeAheadField.markInvalidProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue && !newValue && needValidation) {
					validate();
				}
			}
		});

		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (detectTextChanged) {
					if (popup != null && !popup.isShowing()) {
						try {
							if (newValue.length() < oldValue.length()) {
								//user is trying to nullify the value
								needValidation = true;
								return;
							}
						} catch (NullPointerException npe) {
							//do nothing for NPE
						}
					}

					if (waitTimer != null) {
						loaderTimerTask.setObsolete(true);
						waitTimer.cancel();
						waitTimer.purge();
					}

					typeAheadField.markInvalidProperty().set(true);
					needValidation = true;

					waitTimer = new Timer("lookupTimer");
					loaderTimerTask = new LoaderTimerTask(waitTimer);
					waitTimer.schedule(loaderTimerTask, 100);
				}
			}
		});
		
		typeAheadField.showingSuggestionProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
				if (t1) {
					showSuggestion();
				} else {
					hideSuggestion();
				}
			}
		});
	}

	private void updateTextField() {
		detectTextChanged = false;
		needValidation = false;
		if (typeAheadField.getValue() == null) {
			textField.setText("");
			typeAheadField.markInvalidProperty().set(false);
			detectTextChanged = true;
			return;
		}
		T value = getSkinnable().getValue();
		if (value != null) {
			String string = getSkinnable().getConverter().toString(value);
			if (string == null) {
				textField.setText("");
			} else {
				textField.setText(string);
			}
		} else {
			textField.setText("");
		}
		typeAheadField.markInvalidProperty().set(false);
		detectTextChanged = true;
	}

	private Point2D getPrefPopupPosition() {
		Point2D p = getSkinnable().localToScene(0.0, 0.0);
		Point2D p2 = new Point2D(p.getX() + getSkinnable().getScene().getX() + getSkinnable().getScene().getWindow().getX(), p.getY() + getSkinnable().getScene().getY() + getSkinnable().getScene().getWindow().getY() + getSkinnable().getHeight());
		return p2;
	}

	private void positionAndShowPopup() {

		Point2D p = getPrefPopupPosition();

		/**
		 * In LookupColumn, sometimes the lookupfield disappears due to commit editing before the popup appears. In this case, lookupField.getScene() will be null.
		 */
		Scene scene = typeAheadField.getScene();
		if (scene != null) {
			getPopup().show(scene.getWindow(), p.getX(), p.getY());
			listView.scrollTo(getSkinnable().getValue());
		}
	}

	@Override
	protected void layoutChildren(final double x, final double y, final double w, final double h) {

		double obw = button.prefWidth(-1);

		double displayWidth = getSkinnable().getWidth()
				  - (getSkinnable().getInsets().getLeft() + getSkinnable().getInsets().getRight() + obw);

		textField.resizeRelocate(x, y, w, h);
		button.resizeRelocate(x + displayWidth, y, obw, h);
	}

	private List<T> getData() {
		List<T> items = getSkinnable().getItems();
		StringConverter<T> converter = getSkinnable().getConverter();
		List<T> eligibleItems = new ArrayList<>();
		String text = textField.getText().substring(0, textField.getCaretPosition());
		if (!getSkinnable().isSorted() && (text == null || text.length() == 0)) {
			return items;
		}

		if (getSkinnable().isSorted()) {
			List<String> lstEligibleString = new ArrayList<>();
			for (T item : items) {
				String label = converter.toString(item);
				if (label != null && label.toLowerCase().startsWith(text.toLowerCase())) {
					lstEligibleString.add(label);
				}
			}
			Collections.sort(lstEligibleString);
			for (String string : lstEligibleString) {
				eligibleItems.add(converter.fromString(string));
			}
		} else {
			for (T item : items) {
				String label = converter.toString(item);
				if (label != null && label.toLowerCase().startsWith(text.toLowerCase())) {
					eligibleItems.add(item);
				}
			}
		}
		return eligibleItems;
	}

	private void validate() {
		if (needValidation) {
			if (!textField.getText().isEmpty()) {
				loaderTimerTask.setObsolete(true);
				List<T> data = getData();
				if (data.size() > 0) {
					if (typeAheadField.getValue() == data.get(0)) {
						updateTextField();
					} else {
						typeAheadField.setValue(data.get(0));
					}
				} else if (typeAheadField.getValue() == null) {
					//need to update text field since value change listener
					//doesn't detect any change.
					updateTextField();
				} else {
					//the text field will be updated by value change listener
					typeAheadField.setValue(null);
				}
			} else {
				typeAheadField.setValue(null);
			}
		}
	}

	private void makeSelectionOnList() {
		boolean dummyBag = needValidation;
		needValidation = false;
		if (getSkinnable().getValue() == null) {
			listView.getSelectionModel().selectFirst();
			listView.scrollTo(0);
		} else {
			if (listView.getItems().contains(getSkinnable().getValue())) {
				listView.getSelectionModel().select(getSkinnable().getValue());
				listView.scrollTo(getSkinnable().getValue());
			} else {
				listView.getSelectionModel().selectFirst();
				listView.scrollTo(0);
			}
		}

		needValidation = dummyBag;
	}

	private void showSuggestion() {
		List<T> data = getData();
		listView.getItems().clear();
		listView.getItems().addAll(data);
		makeSelectionOnList();
		positionAndShowPopup();
	}

	private void refreshList() {
		List<T> data = getData();
		listView.getItems().clear();
		listView.getItems().addAll(data);
		makeSelectionOnList();
	}

	private class LoaderTimerTask extends TimerTask {

		private boolean obsolete = false;
		private Timer timer;

		public LoaderTimerTask(Timer timer) {
			this.timer = timer;
		}

		public void setObsolete(boolean obsolete) {
			this.obsolete = obsolete;
		}

		@Override
		public void run() {
			if (!obsolete) {
				final List<T> data = getData();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (!obsolete) {
							listView.getItems().clear();
							if (!data.isEmpty()) {
								listView.getItems().addAll(data);
								listView.getSelectionModel().select(data.get(0));
								typeAheadField.showSuggestion();
							}
						}
					}
				});
			}
			timer.cancel();
			timer.purge();
		}
	}
	private ListView<T> listView = new ListView<T>() {
		@Override
		protected double computeMinHeight(double width) {
			return 30;
		}

		@Override
		protected double computePrefWidth(double height) {
			double pw = 0;
			if (getSkin() instanceof ListViewSkin) {
				ListViewSkin<?> skin = (ListViewSkin<?>) getSkin();

				int rowsToMeasure = -1;

//				pw = Math.max(comboBox.getWidth(), skin.getMaxCellWidth(rowsToMeasure) + 30);
				Method method;
				try {
					method = skin.getClass().getSuperclass().getDeclaredMethod("getMaxCellWidth", Integer.TYPE);

					method.setAccessible(true);
					double rowWidth = (double) method.invoke(skin, rowsToMeasure);
					pw = Math.max(typeAheadField.getWidth(), rowWidth + 30);
				} catch (Exception ex) {
					Logger.getLogger(TypeAheadFieldSkin.class.getName()).log(Level.SEVERE, null, ex);
				}

			} else {
				pw = Math.max(100, typeAheadField.getWidth());
			}

			// need to check the ListView pref height in the case that the
			// placeholder node is showing
			if (getItems().isEmpty() && getPlaceholder() != null) {
				pw = Math.max(super.computePrefWidth(height), pw);
			}

			return Math.max(50, pw);
		}

		@Override
		protected double computePrefHeight(double width) {

			return getListViewPrefHeight();
		}
	};

	private double getListViewPrefHeight() {
		double ph;
//		if (listView.getSkin() instanceof VirtualContainerBase) {
//			int maxRows = 10;
//			VirtualContainerBase<?, ?> skin = (VirtualContainerBase<?, ?>) listView.getSkin();
//			ph = skin.getVirtualFlowPreferredHeight(maxRows);
//		} else {
		double ch = typeAheadField.getItems().size() * 25;
		ph = Math.min(ch, 200);
//		}

		return ph;
	}

	private class PropertyListCell extends ListCell<T> {

		@Override
		protected void updateItem(T t, boolean bln) {
			super.updateItem(t, bln);

			if (t != null) {
				StringConverter<T> converter = getSkinnable().getConverter();
				String value = converter.toString(t);
				if (value != null) {
					setText(value.toString());
				} else {
					setText("");
				}
			} else {
				setText("");
			}
		}
	}
	
	@Override
	protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
		return 50;
	}
	
	@Override
	protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
		return getSkinnable().prefHeight(width);
	}
	
}
