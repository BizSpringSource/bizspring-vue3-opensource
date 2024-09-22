package cn.bizspring.cloud.upms.common.util;

import cn.bizspring.cloud.upms.common.dto.MenuTree;
import cn.bizspring.cloud.upms.common.dto.TreeNode;
import cn.bizspring.cloud.upms.common.entity.Menu;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author
 */
@UtilityClass
public class TreeUtil {
	/**
	 * 两层循环实现建树
	 *
	 * @param treeNodes 传入的树节点列表
	 * @return
	 */
	public <T extends TreeNode> List<T> build(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<>();
		for (T treeNode : treeNodes) {
			if (root.equals(treeNode.getParentId())) {
				trees.add(treeNode);
//				trees.sort(Comparator.comparing(TreeNode::getSort));
			}
			for (T it : treeNodes) {
				if (it.getParentId().equals(treeNode.getId())) {
					treeNode.addChildren(it);
//					treeNode.getChildren().sort(Comparator.comparing(TreeNode::getSort));
				}
			}
		}
		return trees;
	}

	/**
	 * 使用递归方法建树
	 *
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<T>();
		for (T treeNode : treeNodes) {
			if (root.equals(treeNode.getParentId())) {
				trees.add(findChildren(treeNode, treeNodes));
			}
		}
		return trees;
	}

	/**
	 * 递归查找子节点
	 *
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
		for (T it : treeNodes) {
			if (treeNode.getId() == it.getParentId()) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<>());
				}
				treeNode.addChildren(findChildren(it, treeNodes));
			}
		}
		return treeNode;
	}

	/**
	 * 通过menu创建树形节点
	 *
	 * @param menus
	 * @param root
	 * @return
	 */
	public List<MenuTree> buildTree(List<Menu> menus, String root) {
		List<MenuTree> trees = new ArrayList<>();
		MenuTree node;
		for (Menu menu : menus) {
			node = new MenuTree();
			node.setType(menu.getType());
			node.setId(menu.getId());
			node.setParentId(menu.getParentId());
			node.setName(menu.getName());
			node.setPath(menu.getPath());
			node.setPermission(menu.getPermission());
//			node.setI18nCode(menu.getI18nCode());
			node.setLabel(menu.getName());
			node.setComponent(menu.getComponent());
			node.setIcon(menu.getIcon());
			node.setSort(menu.getSort());
			node.setKeepAlive(menu.getKeepAlive());
			trees.add(node);
		}
		return TreeUtil.build(trees, root);
	}
}
