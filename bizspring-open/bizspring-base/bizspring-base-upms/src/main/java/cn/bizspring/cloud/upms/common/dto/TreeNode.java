package cn.bizspring.cloud.upms.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Data
public class TreeNode {
	protected String id;
	protected String parentId;
	private Integer sort;
	protected List<TreeNode> children = new ArrayList<>();

	public void addChildren(TreeNode treeNode) {
		children.add(treeNode);
	}

	public List<TreeNode> getChildren() {
		if(children.size()<=0){
			return null;
		}
		return children;
	}
}
