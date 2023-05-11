package components.tabpage;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import com.sun.javafx.scene.control.skin.PaginationSkin;
import com.sun.javafx.scene.control.skin.Utils;
import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.animation.Interpolator;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.css.CssMetaData;
import javafx.css.StyleableDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.AccessibleAction;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * @see com.sun.javafx.scene.control.skin.PaginationSkin
 */
public class TablePaginationSkin extends BehaviorSkinBase<TablePagination, TablePaginationBehavior> {

    /**
     * TODO 替换成TablePagination
     */
    private Pagination pagination;

    public TablePaginationSkin(TablePagination control) {
        this(control, new TablePaginationBehavior(control, new ArrayList<>()));
    }

    /**
     * Constructor for all BehaviorSkinBase instances.
     * @param control  The control for which this Skin should attach to.
     * @param behavior The behavior for which this Skin should defer to.
     */
    protected TablePaginationSkin(TablePagination control, TablePaginationBehavior behavior) {
        super(control, behavior);
    }

    private NavigationControl navigation;
    private int fromIndex;
    private int previousIndex;
    private int currentIndex;
    private int toIndex;
    private int pageCount;
    private int maxPageIndicatorCount;

    private boolean animate = true;


    private static final Interpolator interpolator = Interpolator.SPLINE(0.4829, 0.5709, 0.6803, 0.9928);
    private int currentAnimatedIndex;
    private boolean hasPendingAnimation = false;

    /**
     * The size of the gap between number buttons and arrow buttons
     */
    private final DoubleProperty arrowButtonGap = new StyleableDoubleProperty(60.0) {
        @Override
        public Object getBean() {
            return TablePaginationSkin.this;
        }

        @Override
        public String getName() {
            return "arrowButtonGap";
        }

        @Override
        public CssMetaData<Pagination, Number> getCssMetaData() {
            /**
             * 暂时不添加CSS支持
             * @see PaginationSkin 522行
             */
            return null;
        }
    };

    public void selectPrevious() {
        if (getCurrentPageIndex() > 0) {
            pagination.setCurrentPageIndex(getCurrentPageIndex() - 1);
        }
    }

    private int getPageCount() {
        if (pagination.getPageCount() < 1) {
            return 1;
        }
        return pagination.getPageCount();
    }

    private int getMaxPageIndicatorCount() {
        return pagination.getMaxPageIndicatorCount();
    }

    private int getCurrentPageIndex() {
        return pagination.getCurrentPageIndex();
    }

    class NavigationControl extends StackPane {

        private HBox controlBox;
        private Button leftArrowButton;
        private StackPane leftArrow;
        private Button rightArrowButton;
        private StackPane rightArrow;
        private ToggleGroup indicatorButtons;
        private Label pageInformation;
        private double previousWidth = -1;
        private double minButtonSize = -1;


        public NavigationControl() {
            getStyleClass().setAll("pagination-control");

            // redirect mouse events to behavior
            addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> getBehavior().mousePressed(e));
            addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> getBehavior().mouseReleased(e));
            addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> getBehavior().mouseEntered(e));
            addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> getBehavior().mouseExited(e));

            controlBox = new HBox();
            controlBox.getStyleClass().add("control-box");

            leftArrowButton = new Button();
            leftArrowButton.setAccessibleText(ControlResources.getString("Accessibility.title.Pagination.PreviousButton"));
            minButtonSize = leftArrowButton.getFont().getSize() * 2;
            leftArrowButton.fontProperty().addListener((arg0, arg1, newFont) -> {
                minButtonSize = newFont.getSize() * 2;
                for (Node child : controlBox.getChildren()) {
                    ((Control) child).setMinSize(minButtonSize, minButtonSize);
                }
                // We want to relayout the indicator buttons because the size has changed.
                requestLayout();
            });
            leftArrowButton.setMinSize(minButtonSize, minButtonSize);
            leftArrowButton.prefWidthProperty().bind(leftArrowButton.minWidthProperty());
            leftArrowButton.prefHeightProperty().bind(leftArrowButton.minHeightProperty());
            leftArrowButton.getStyleClass().add("left-arrow-button");
            leftArrowButton.setFocusTraversable(false);
            HBox.setMargin(leftArrowButton, new Insets(0, snapSize(arrowButtonGap.get()), 0, 0));
            leftArrow = new StackPane();
            leftArrow.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
            leftArrowButton.setGraphic(leftArrow);
            leftArrow.getStyleClass().add("left-arrow");

            rightArrowButton = new Button();
            rightArrowButton.setAccessibleText(ControlResources.getString("Accessibility.title.Pagination.NextButton"));
            rightArrowButton.setMinSize(minButtonSize, minButtonSize);
            rightArrowButton.prefWidthProperty().bind(rightArrowButton.minWidthProperty());
            rightArrowButton.prefHeightProperty().bind(rightArrowButton.minHeightProperty());
            rightArrowButton.getStyleClass().add("right-arrow-button");
            rightArrowButton.setFocusTraversable(false);
            HBox.setMargin(rightArrowButton, new Insets(0, 0, 0, snapSize(arrowButtonGap.get())));
            rightArrow = new StackPane();
            rightArrow.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
            rightArrowButton.setGraphic(rightArrow);
            rightArrow.getStyleClass().add("right-arrow");

            indicatorButtons = new ToggleGroup();

            pageInformation = new Label();
            pageInformation.getStyleClass().add("page-information");

            getChildren().addAll(controlBox, pageInformation);
            initializeNavigationHandlers();
            initializePageIndicators();
            updatePageIndex();

            /**
             * @see PaginationSkin
             */
            // listen to changes to arrowButtonGap and update margins
            arrowButtonGap.addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() == 0) {
                    HBox.setMargin(leftArrowButton, null);
                    HBox.setMargin(rightArrowButton, null);

                } else {
                    HBox.setMargin(leftArrowButton, new Insets(0, snapSize(newValue.doubleValue()), 0, 0));
                    HBox.setMargin(rightArrowButton, new Insets(0, 0, 0, snapSize(newValue.doubleValue())));
                }
            });
        }

        private void initializeNavigationHandlers() {
            leftArrowButton.setOnAction(arg0 -> {
                selectPrevious();
                requestLayout();
            });

            rightArrowButton.setOnAction(arg0 -> {
                // selectNext();
                requestLayout();
            });

            pagination.currentPageIndexProperty().addListener((arg0, arg1, arg2) -> {
                previousIndex = arg1.intValue();
                currentIndex = arg2.intValue();
                updatePageIndex();
                if (animate) {
                    currentAnimatedIndex = currentIndex;
                    // animateSwitchPage();
                } else {
                    // createPage(currentStackPane, currentIndex);
                }
            });
        }

        // Create the indicators using fromIndex and toIndex.
        private void initializePageIndicators() {
            previousIndicatorCount = 0;
            controlBox.getChildren().clear();
            clearIndicatorButtons();

            controlBox.getChildren().add(leftArrowButton);
            for (int i = fromIndex; i <= toIndex; i++) {
                IndicatorButton ib = new IndicatorButton(i);
                ib.setMinSize(minButtonSize, minButtonSize);
                ib.setToggleGroup(indicatorButtons);
                controlBox.getChildren().add(ib);
            }
            controlBox.getChildren().add(rightArrowButton);
        }

        private void clearIndicatorButtons() {
            for (Toggle toggle : indicatorButtons.getToggles()) {
                if (toggle instanceof IndicatorButton) {
                    IndicatorButton indicatorButton = (IndicatorButton) toggle;
                    indicatorButton.release();
                }
            }
            indicatorButtons.getToggles().clear();
        }

        // Finds and selects the IndicatorButton using the currentIndex.
        private void updatePageIndicators() {
            for (int i = 0; i < indicatorButtons.getToggles().size(); i++) {
                IndicatorButton ib = (IndicatorButton) indicatorButtons.getToggles().get(i);
                if (ib.getPageNumber() == currentIndex) {
                    ib.setSelected(true);
                    updatePageInformation();
                    break;
                }
            }
            getSkinnable().notifyAccessibleAttributeChanged(AccessibleAttribute.FOCUS_ITEM);
        }

        // Update the page index using the currentIndex and updates the page set
        // if necessary.
        private void updatePageIndex() {
            //System.out.println("SELECT PROPERTY FROM " + fromIndex + " TO " + toIndex + " PREVIOUS " + previousIndex + " CURRENT "+ currentIndex + " PAGE COUNT " + pageCount + " MAX PAGE INDICATOR COUNT " + maxPageIndicatorCount);
            if (pageCount == maxPageIndicatorCount) {
                if (changePageSet()) {
                    initializePageIndicators();
                }
            }
            updatePageIndicators();
            requestLayout();
        }

        private void updatePageInformation() {
            String currentPageNumber = Integer.toString(currentIndex + 1);
            String lastPageNumber = getPageCount() == Pagination.INDETERMINATE ? "..." : Integer.toString(getPageCount());
            pageInformation.setText(currentPageNumber + "/" + lastPageNumber);
        }

        private int previousIndicatorCount = 0;

        // Layout the maximum number of page indicators we can fit within the width.
        // And always show the selected indicator.
        private void layoutPageIndicators() {
            final double left = snappedLeftInset();
            final double right = snappedRightInset();
            final double width = snapSize(getWidth()) - (left + right);
            final double controlBoxleft = controlBox.snappedLeftInset();
            final double controlBoxRight = controlBox.snappedRightInset();
            final double leftArrowWidth = snapSize(Utils.boundedSize(leftArrowButton.prefWidth(-1), leftArrowButton.minWidth(-1), leftArrowButton.maxWidth(-1)));
            final double rightArrowWidth = snapSize(Utils.boundedSize(rightArrowButton.prefWidth(-1), rightArrowButton.minWidth(-1), rightArrowButton.maxWidth(-1)));
            final double spacing = snapSize(controlBox.getSpacing());
            double w = width - (controlBoxleft + leftArrowWidth + 2 * arrowButtonGap.get() + spacing + rightArrowWidth + controlBoxRight);

            if (isPageInformationVisible() &&
                    (Side.LEFT.equals(getPageInformationAlignment()) ||
                            Side.RIGHT.equals(getPageInformationAlignment()))) {
                w -= snapSize(pageInformation.prefWidth(-1));
            }

            double x = 0;
            int indicatorCount = 0;
            for (int i = 0; i < getMaxPageIndicatorCount(); i++) {
                int index = i < indicatorButtons.getToggles().size() ? i : indicatorButtons.getToggles().size() - 1;
                double iw = minButtonSize;
                if (index != -1) {
                    IndicatorButton ib = (IndicatorButton) indicatorButtons.getToggles().get(index);
                    iw = snapSize(Utils.boundedSize(ib.prefWidth(-1), ib.minWidth(-1), ib.maxWidth(-1)));
                }

                x += (iw + spacing);
                if (x > w) {
                    break;
                }
                indicatorCount++;
            }
            if (indicatorCount == 0) {
                indicatorCount = 1; // The parent didn't respect the minSize of this Pagination.
                // We will show at least one indicator nonetheless.
            }

            if (indicatorCount != previousIndicatorCount) {
                if (indicatorCount < getMaxPageIndicatorCount()) {
                    maxPageIndicatorCount = indicatorCount;
                } else {
                    maxPageIndicatorCount = getMaxPageIndicatorCount();
                }

                int lastIndicatorButtonIndex;
                if (pageCount > maxPageIndicatorCount) {
                    pageCount = maxPageIndicatorCount;
                    lastIndicatorButtonIndex = maxPageIndicatorCount - 1;
                } else {
                    if (indicatorCount > getPageCount()) {
                        pageCount = getPageCount();
                        lastIndicatorButtonIndex = getPageCount() - 1;
                    } else {
                        pageCount = indicatorCount;
                        lastIndicatorButtonIndex = indicatorCount - 1;
                    }
                }

                if (currentIndex >= toIndex) {
                    // The current index has fallen off the right
                    toIndex = currentIndex;
                    fromIndex = toIndex - lastIndicatorButtonIndex;
                } else if (currentIndex <= fromIndex) {
                    // The current index has fallen off the left
                    fromIndex = currentIndex;
                    toIndex = fromIndex + lastIndicatorButtonIndex;
                } else {
                    toIndex = fromIndex + lastIndicatorButtonIndex;
                }

                if (toIndex > getPageCount() - 1) {
                    toIndex = getPageCount() - 1;
                    //fromIndex = toIndex - lastIndicatorButtonIndex;
                }

                if (fromIndex < 0) {
                    fromIndex = 0;
                    toIndex = fromIndex + lastIndicatorButtonIndex;
                }

                initializePageIndicators();
                updatePageIndicators();
                previousIndicatorCount = indicatorCount;
            }
        }

        // Only change to the next set when the current index is at the start or the end of the set.
        // Return true only if we have scrolled to the next/previous set.
        private boolean changePageSet() {
            int index = indexToIndicatorButtonsIndex(currentIndex);
            int lastIndicatorButtonIndex = maxPageIndicatorCount - 1;
            if (previousIndex < currentIndex &&
                    index == 0 &&
                    lastIndicatorButtonIndex != 0 &&
                    index % lastIndicatorButtonIndex == 0) {
                // Get the right page set
                fromIndex = currentIndex;
                toIndex = fromIndex + lastIndicatorButtonIndex;
            } else if (currentIndex < previousIndex &&
                    index == lastIndicatorButtonIndex &&
                    lastIndicatorButtonIndex != 0 &&
                    index % lastIndicatorButtonIndex == 0) {
                // Get the left page set
                toIndex = currentIndex;
                fromIndex = toIndex - lastIndicatorButtonIndex;
            } else {
                // We need to get the new page set if the currentIndex is out of range.
                // This can happen if setPageIndex() is called programatically.
                if (currentIndex < fromIndex || currentIndex > toIndex) {
                    fromIndex = currentIndex - index;
                    toIndex = fromIndex + lastIndicatorButtonIndex;
                } else {
                    return false;
                }
            }

            // We have gone past the total number of pages
            if (toIndex > getPageCount() - 1) {
                if (fromIndex > getPageCount() - 1) {
                    return false;
                } else {
                    toIndex = getPageCount() - 1;
                    //fromIndex = toIndex - lastIndicatorButtonIndex;
                }
            }

            // We have gone past the starting page
            if (fromIndex < 0) {
                fromIndex = 0;
                toIndex = fromIndex + lastIndicatorButtonIndex;
            }
            return true;
        }

        private int indexToIndicatorButtonsIndex(int index) {
            // This should be in the indicator buttons toggle list.
            if (index >= fromIndex && index <= toIndex) {
                return index - fromIndex;
            }
            // The requested index is not in indicator buttons list we have to predict
            // where the index will be.
            int i = 0;
            int from = fromIndex;
            int to = toIndex;
            if (currentIndex > previousIndex) {
                while (from < getPageCount() && to < getPageCount()) {
                    from += i;
                    to += i;
                    if (index >= from && index <= to) {
                        if (index == from) {
                            return 0;
                        } else if (index == to) {
                            return maxPageIndicatorCount - 1;
                        }
                        return index - from;
                    }
                    i += maxPageIndicatorCount;
                }
            } else {
                while (from > 0 && to > 0) {
                    from -= i;
                    to -= i;
                    if (index >= from && index <= to) {
                        if (index == from) {
                            return 0;
                        } else if (index == to) {
                            return maxPageIndicatorCount - 1;
                        }
                        return index - from;
                    }
                    i += maxPageIndicatorCount;
                }
            }
            // We are on the last page set going back to the previous page set
            return maxPageIndicatorCount - 1;
        }

        private Pos sideToPos(Side s) {
            if (Side.TOP.equals(s)) {
                return Pos.TOP_CENTER;
            } else if (Side.RIGHT.equals(s)) {
                return Pos.CENTER_RIGHT;
            } else if (Side.BOTTOM.equals(s)) {
                return Pos.BOTTOM_CENTER;
            }
            return Pos.CENTER_LEFT;
        }

        @Override
        protected double computeMinWidth(double height) {
            double left = snappedLeftInset();
            double right = snappedRightInset();
            double leftArrowWidth = snapSize(Utils.boundedSize(leftArrowButton.prefWidth(-1), leftArrowButton.minWidth(-1), leftArrowButton.maxWidth(-1)));
            double rightArrowWidth = snapSize(Utils.boundedSize(rightArrowButton.prefWidth(-1), rightArrowButton.minWidth(-1), rightArrowButton.maxWidth(-1)));
            double spacing = snapSize(controlBox.getSpacing());
            double pageInformationWidth = 0;
            // Side side = getPageInformationAlignment();
            Side side = Side.LEFT;
            if (Side.LEFT.equals(side) || Side.RIGHT.equals(side)) {
                pageInformationWidth = snapSize(pageInformation.prefWidth(-1));
            }
            double arrowGap = arrowButtonGap.get();

            return left + leftArrowWidth + 2 * arrowGap + minButtonSize /*at least one button*/
                    + 2 * spacing + rightArrowWidth + right + pageInformationWidth;
        }

        @Override
        protected double computeMinHeight(double width) {
            return computePrefHeight(width);
        }

        @Override
        protected double computePrefWidth(double height) {
            final double left = snappedLeftInset();
            final double right = snappedRightInset();
            final double controlBoxWidth = snapSize(controlBox.prefWidth(height));
            double pageInformationWidth = 0;
            Side side = getPageInformationAlignment();
            if (Side.LEFT.equals(side) || Side.RIGHT.equals(side)) {
                pageInformationWidth = snapSize(pageInformation.prefWidth(-1));
            }

            return left + controlBoxWidth + right + pageInformationWidth;
        }

        @Override
        protected double computePrefHeight(double width) {
            final double top = snappedTopInset();
            final double bottom = snappedBottomInset();
            final double boxHeight = snapSize(controlBox.prefHeight(width));
            double pageInformationHeight = 0;
            Side side = getPageInformationAlignment();
            if (Side.TOP.equals(side) || Side.BOTTOM.equals(side)) {
                pageInformationHeight = snapSize(pageInformation.prefHeight(-1));
            }

            return top + boxHeight + pageInformationHeight + bottom;
        }

        @Override
        protected void layoutChildren() {
            final double top = snappedTopInset();
            final double bottom = snappedBottomInset();
            final double left = snappedLeftInset();
            final double right = snappedRightInset();
            final double width = snapSize(getWidth()) - (left + right);
            final double height = snapSize(getHeight()) - (top + bottom);
            final double controlBoxWidth = snapSize(controlBox.prefWidth(-1));
            final double controlBoxHeight = snapSize(controlBox.prefHeight(-1));
            final double pageInformationWidth = snapSize(pageInformation.prefWidth(-1));
            final double pageInformationHeight = snapSize(pageInformation.prefHeight(-1));

            leftArrowButton.setDisable(false);
            rightArrowButton.setDisable(false);

            if (currentIndex == 0) {
                // Grey out the left arrow if we are at the beginning.
                leftArrowButton.setDisable(true);
            }
            if (currentIndex == (getPageCount() - 1)) {
                // Grey out the right arrow if we have reached the end.
                rightArrowButton.setDisable(true);
            }
            // Reapply CSS so the left and right arrow button's disable state is updated
            // immediately.
            applyCss();

            leftArrowButton.setVisible(isArrowsVisible());
            rightArrowButton.setVisible(isArrowsVisible());
            pageInformation.setVisible(isPageInformationVisible());

            // Determine the number of indicators we can fit within the pagination width.
//            if (snapSize(getWidth()) != previousWidth) {
            layoutPageIndicators();
//            }
            previousWidth = getWidth();

            HPos controlBoxHPos = controlBox.getAlignment().getHpos();
            VPos controlBoxVPos = controlBox.getAlignment().getVpos();
//            double controlBoxX = left + Utils.computeXOffset(width, controlBoxWidth, controlBoxHPos);
//            double controlBoxY = top + Utils.computeYOffset(height, controlBoxHeight, controlBoxVPos);

            double controlBoxX = 0;
            double controlBoxY = 0;

            if (isPageInformationVisible()) {
                Pos p = sideToPos(getPageInformationAlignment());
                HPos pageInformationHPos = p.getHpos();
                VPos pageInformationVPos = p.getVpos();
//                double pageInformationX = left + Utils.computeXOffset(width, pageInformationWidth, pageInformationHPos);
//                double pageInformationY = top + Utils.computeYOffset(height, pageInformationHeight, pageInformationVPos);
                double pageInformationX = 0;
                double pageInformationY = 0;

                if (Side.TOP.equals(getPageInformationAlignment())) {
                    pageInformationY = top;
                    controlBoxY = top + pageInformationHeight;
                } else if (Side.RIGHT.equals(getPageInformationAlignment())) {
                    pageInformationX = width - right - pageInformationWidth;
                } else if (Side.BOTTOM.equals(getPageInformationAlignment())) {
                    controlBoxY = top;
                    pageInformationY = top + controlBoxHeight;
                } else if (Side.LEFT.equals(getPageInformationAlignment())) {
                    pageInformationX = left;
                }
                layoutInArea(pageInformation, pageInformationX, pageInformationY, pageInformationWidth, pageInformationHeight, 0, pageInformationHPos, pageInformationVPos);
            }

            layoutInArea(controlBox, controlBoxX, controlBoxY, controlBoxWidth, controlBoxHeight, 0, controlBoxHPos, controlBoxVPos);
        }
    }

    private boolean isArrowsVisible() {
        return false;
    }

    private boolean isPageInformationVisible() {
        return false;
    }

    private Side getPageInformationAlignment() {
        return null;
    }


    class IndicatorButton extends ToggleButton {
        private final ListChangeListener<String> updateSkinIndicatorType =
                c -> setIndicatorType();

        private final ChangeListener<Boolean> updateTooltipVisibility =
                (ob, oldValue, newValue) -> setTooltipVisible(newValue);

        private int pageNumber;

        public IndicatorButton(int pageNumber) {
            this.pageNumber = pageNumber;
            setFocusTraversable(false);
            setIndicatorType();
            setTooltipVisible(isTooltipVisible());

            getSkinnable().getStyleClass().addListener(updateSkinIndicatorType);

            setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    int selected = getCurrentPageIndex();
                    // We do not need to update the selection if it has not changed.
                    if (selected != IndicatorButton.this.pageNumber) {
                        pagination.setCurrentPageIndex(IndicatorButton.this.pageNumber);
                        requestLayout();
                    }
                }
            });

            tooltipVisibleProperty().addListener((InvalidationListener) updateTooltipVisibility);

            prefHeightProperty().bind(minHeightProperty());
            setAccessibleRole(AccessibleRole.PAGE_ITEM);
        }

        private void setIndicatorType() {
            if (getSkinnable().getStyleClass().contains(Pagination.STYLE_CLASS_BULLET)) {
                getStyleClass().remove("number-button");
                getStyleClass().add("bullet-button");
                setText(null);

                // Bind the width in addition to the height to ensure the region is square
                prefWidthProperty().bind(minWidthProperty());
            } else {
                getStyleClass().remove("bullet-button");
                getStyleClass().add("number-button");
                setText(Integer.toString(this.pageNumber + 1));

                // Free the width to conform to the text content
                prefWidthProperty().unbind();
            }
        }

        private void setTooltipVisible(boolean b) {
            if (b) {
                setTooltip(new Tooltip(Integer.toString(IndicatorButton.this.pageNumber + 1)));
            } else {
                setTooltip(null);
            }
        }

        public int getPageNumber() {
            return this.pageNumber;
        }

        @Override
        public void fire() {
            // we don't toggle from selected to not selected if part of a group
            if (getToggleGroup() == null || !isSelected()) {
                super.fire();
            }
        }

        public void release() {
            getSkinnable().getStyleClass().removeListener(updateSkinIndicatorType);
            tooltipVisibleProperty().removeListener((InvalidationListener) updateTooltipVisibility);
        }

        @Override
        public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
            switch (attribute) {
                case TEXT:
                    return getText();
                case SELECTED:
                    return isSelected();
                default:
                    return super.queryAccessibleAttribute(attribute, parameters);
            }
        }

        @Override
        public void executeAccessibleAction(AccessibleAction action, Object... parameters) {
            switch (action) {
                case REQUEST_FOCUS:
                    getSkinnable().setCurrentPageIndex(pageNumber);
                    break;
                default:
                    super.executeAccessibleAction(action);
            }
        }

        /**
         * TODO 换成实际的类型
         * @return
         */
        public Pagination getSkinnable() {
            return new Pagination();
        }
    }

    private boolean isTooltipVisible() {
        return false;
    }

    private Observable tooltipVisibleProperty() {
        return null;
    }
}
