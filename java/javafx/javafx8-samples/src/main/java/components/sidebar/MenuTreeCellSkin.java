package components.sidebar;

import com.sun.javafx.css.converters.SizeConverter;
import com.sun.javafx.scene.control.MultiplePropertyChangeListenerHandler;
import com.sun.javafx.scene.control.skin.CellSkinBase;
import com.sun.javafx.scene.control.skin.TreeCellSkin;
import javafx.beans.property.DoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.css.StyleableProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.*;

/**
 * @see com.sun.javafx.scene.control.skin.TreeCellSkin
 */
public class MenuTreeCellSkin extends CellSkinBase<MenuTreeCell, MenuTreeCellBehavior> {

    /*
     * This is rather hacky - but it is a quick workaround to resolve the
     * issue that we don't know maximum width of a disclosure node for a given
     * TreeView. If we don't know the maximum width, we have no way to ensure
     * consistent indentation for a given TreeView.
     *
     * To work around this, we create a single WeakHashMap to store a max
     * disclosureNode width per TreeView. We use WeakHashMap to help prevent
     * any memory leaks.
     *
     * RT-19656 identifies a related issue, which is that we may not provide
     * indentation to any TreeItems because we have not yet encountered a cell
     * which has a disclosureNode. Once we scroll and encounter one, indentation
     * happens in a displeasing way.
     */
    private static final Map<TreeView<?>, Double> maxDisclosureWidthMap = new WeakHashMap<>();

    private TreeItem<?> treeItem;

    /**
     * The amount of space to multiply by the treeItem.Level to get the left
     * margin for this tree cell. This is settable from CSS
     */
    private DoubleProperty indent = null;

    public final void setIndent(double value) {
        indentProperty().set(value);
    }

    /**
     * 默认缩进为10
     * @return indent
     */
    public final double getIndent() {
        return indent == null ? 10.0 : indent.get();
    }

    public final DoubleProperty indentProperty() {
        if (indent == null) {
            indent = new StyleableDoubleProperty(10.0) {
                @Override
                public Object getBean() {
                    return MenuTreeCellSkin.this;
                }

                @Override
                public String getName() {
                    return "indent";
                }

                @Override
                public CssMetaData<MenuTreeCell, Number> getCssMetaData() {
                    return MenuTreeCellSkin.StyleableProperties.INDENT;
                }
            };
        }
        return indent;
    }

    public MenuTreeCellSkin(MenuTreeCell control) {
        super(control, new MenuTreeCellBehavior(control, Collections.emptyList()));

        this.fixedCellSize = control.getTreeView().getFixedCellSize();
        this.fixedCellSizeEnabled = fixedCellSize > 0;

        updateTreeItem();
        updateDisclosureNodeRotation(false);

        registerChangeListener(control.treeItemProperty(), "TREE_ITEM");
        registerChangeListener(control.textProperty(), "TEXT");
        registerChangeListener(control.getTreeView().fixedCellSizeProperty(), "FIXED_CELL_SIZE");
    }

    private void updateDisclosureNodeRotation(boolean animate) {

    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        // RT-25876: can not null-check here as this prevents empty rows from
        // being cleaned out.
        // if (treeItem == null) return;

        TreeView<String> tree = getSkinnable().getTreeView();
        if (tree == null) return;

        if (disclosureNodeDirty) {
            updateDisclosureNode();
            disclosureNodeDirty = false;
        }

        final Node disclosureNode = getSkinnable().getDisclosureNode();

        int level = tree.getTreeItemLevel(treeItem);
        if (!tree.isShowRoot()) {
            level--;
        }
        double leftMargin = getIndent() * level;

        x += leftMargin;

        // position the disclosure node so that it is at the proper indent
        boolean disclosureVisible = disclosureNode != null && treeItem != null && !treeItem.isLeaf();

        final double defaultDisclosureWidth = maxDisclosureWidthMap.containsKey(tree) ?
                maxDisclosureWidthMap.get(tree) : 18;   // RT-19656: default width of default disclosure node
        double disclosureWidth = defaultDisclosureWidth;

        if (disclosureVisible) {
            if (disclosureNode.getScene() == null) {
                updateChildren();
            }

            disclosureWidth = disclosureNode.prefWidth(h);
            if (disclosureWidth > defaultDisclosureWidth) {
                maxDisclosureWidthMap.put(tree, disclosureWidth);
            }
            double ph = disclosureNode.prefHeight(disclosureWidth);

            double maxX = getSkinnable().getLayoutBounds().getMaxX();

            disclosureNode.resize(disclosureWidth, ph);
            positionInArea(disclosureNode, maxX - x - 20, y,
                    disclosureWidth, ph, /*baseline ignored*/0,
                    HPos.CENTER, VPos.CENTER);
        }

        // determine starting point of the graphic or cell node, and the
        // remaining width available to them
        final int padding = treeItem != null && treeItem.getGraphic() == null ? 0 : 3;
        x += disclosureWidth + padding;
        w -= (leftMargin + disclosureWidth + padding);

        // Rather ugly fix for RT-38519, where graphics are disappearing in
        // certain circumstances
        Node graphic = getSkinnable().getGraphic();
        if (graphic != null && !getChildren().contains(graphic)) {
            getChildren().add(graphic);
        }

        layoutLabelInArea(x, y, w, h);
    }

    private boolean disclosureNodeDirty = true;
    private double fixedCellSize;
    private boolean fixedCellSizeEnabled;

    @Override
    protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if ("TREE_ITEM".equals(p)) {
            updateTreeItem();
            disclosureNodeDirty = true;
            getSkinnable().requestLayout();
        } else if ("TEXT".equals(p)) {
            getSkinnable().requestLayout();
        } else if ("FIXED_CELL_SIZE".equals(p)) {
            this.fixedCellSize = getSkinnable().getTreeView().getFixedCellSize();
            this.fixedCellSizeEnabled = fixedCellSize > 0;
        }
    }

    private MultiplePropertyChangeListenerHandler treeItemListener = new MultiplePropertyChangeListenerHandler(p -> {
        if ("EXPANDED".equals(p)) {
            // 在TreeCellSkin中此方法的内容被注释掉了
            // updateDisclosureNodeRotation(true);
        }
        return null;
    });


    @Override
    protected void updateChildren() {
        super.updateChildren();
        updateDisclosureNode();
    }

    private void updateDisclosureNode() {
        if (getSkinnable().isEmpty()) return;

        Node disclosureNode = getSkinnable().getDisclosureNode();
        if (disclosureNode == null) return;

        boolean disclosureVisible = treeItem != null && !treeItem.isLeaf();
        disclosureNode.setVisible(disclosureVisible);

        if (!disclosureVisible) {
            getChildren().remove(disclosureNode);
        } else if (disclosureNode.getParent() == null) {
            getChildren().add(disclosureNode);
            disclosureNode.toFront();
        } else {
            disclosureNode.toBack();
        }

        // RT-26625: [TreeView, TreeTableView] can lose arrows while scrolling
        // RT-28668: Ensemble tree arrow disappears
        if (disclosureNode.getScene() != null) {
            disclosureNode.applyCss();
        }
    }

    private void updateTreeItem() {
        if (treeItem != null) {
            treeItemListener.unregisterChangeListener(treeItem.expandedProperty());
        }
        treeItem = getSkinnable().getTreeItem();
        if (treeItem != null) {
            treeItemListener.registerChangeListener(treeItem.expandedProperty(), "EXPANDED");
        }
        updateDisclosureNodeRotation(false);
    }

    @Override
    protected double computeMinHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (fixedCellSizeEnabled) {
            return fixedCellSize;
        }

        double pref = super.computeMinHeight(width, topInset, rightInset, bottomInset, leftInset);
        Node d = getSkinnable().getDisclosureNode();
        return (d == null) ? pref : Math.max(d.minHeight(-1), pref);
    }

    @Override
    protected double computePrefHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (fixedCellSizeEnabled) {
            return fixedCellSize;
        }

        final TreeCell<String> cell = getSkinnable();

        final double pref = super.computePrefHeight(width, topInset, rightInset, bottomInset, leftInset);
        final Node d = cell.getDisclosureNode();
        final double prefHeight = (d == null) ? pref : Math.max(d.prefHeight(-1), pref);

        // RT-30212: TreeCell does not honor minSize of cells.
        // snapSize for RT-36460
        return snapSize(Math.max(cell.getMinHeight(), prefHeight));
    }

    @Override
    protected double computeMaxHeight(double width, double topInset, double rightInset, double bottomInset, double leftInset) {
        if (fixedCellSizeEnabled) {
            return fixedCellSize;
        }
        return super.computeMaxHeight(width, topInset, rightInset, bottomInset, leftInset);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        double labelWidth = super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset);
        double pw = snappedLeftInset() + snappedRightInset();
        TreeView<String> tree = getSkinnable().getTreeView();
        if (tree == null) return pw;

        if (treeItem == null) return pw;

        pw = labelWidth;

        // determine the amount of indentation
        int level = tree.getTreeItemLevel(treeItem);
        if (!tree.isShowRoot()) {
            level--;
        }
        pw += getIndent() * level;

        // include the disclosure node width
        Node disclosureNode = getSkinnable().getDisclosureNode();
        double disclosureNodePrefWidth = disclosureNode == null ? 0 : disclosureNode.prefWidth(-1);
        final double defaultDisclosureWidth = maxDisclosureWidthMap.containsKey(tree) ?
                maxDisclosureWidthMap.get(tree) : 0;
        pw += Math.max(defaultDisclosureWidth, disclosureNodePrefWidth);
        return pw;
    }

    private static class StyleableProperties {

        private static final CssMetaData<MenuTreeCell, Number> INDENT =
                new CssMetaData<MenuTreeCell, Number>("-fx-indent",
                        SizeConverter.getInstance(), 10.0) {

                    @Override
                    public boolean isSettable(MenuTreeCell n) {
                        DoubleProperty p = ((TreeCellSkin<?>) n.getSkin()).indentProperty();
                        return p == null || !p.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(MenuTreeCell n) {
                        final MenuTreeCellSkin skin = (MenuTreeCellSkin) n.getSkin();
                        return (StyleableProperty<Number>) skin.indentProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(CellSkinBase.getClassCssMetaData());
            styleables.add(INDENT);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }
}
