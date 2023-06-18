package org.xlp.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>创建时间：2023年6月15日 下午11:01:16</p>
 * @author xlp
 * @version 1.0 
 * @Description 树创建器
*/
public class TreeCreator<T> {
	/**
	 * 存储构造树的数据
	 */
	private List<TreeNode<T>> treeNodes = new ArrayList<TreeNode<T>>(0);
	
	private List<TreeNode<T>> realTree = new ArrayList<TreeNode<T>>();
	
	public TreeCreator(){}
	
	public TreeCreator(List<TreeNode<T>> treeNodes){
		setTreeNodes(treeNodes);
	}

	/**
	 * @return 原始数据
	 */
	public List<TreeNode<T>> getTreeNodes() {
		return treeNodes;
	}

	/**
	 * 设置要创建树的数据
	 * @param treeNodes 
	 */
	public void setTreeNodes(List<TreeNode<T>> treeNodes) {
		if (treeNodes != null) {
			this.treeNodes = treeNodes;
		}
	}
	
	/**
	 * 构造树结构
	 */
	public void tree(){
		Map<T, List<TreeNode<T>>> groupMap = new HashMap<T, List<TreeNode<T>>>();
		groupByParentId(groupMap);
		createTree(groupMap);
	}

	/**
	 * 按照分组后的数据，形成树结构
	 * @param groupMap
	 */
	private void createTree(Map<T, List<TreeNode<T>>> groupMap) {
		Iterator<Entry<T, List<TreeNode<T>>>> iterator = groupMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<T, List<TreeNode<T>>> entry = iterator.next();
			boolean hasFindParent = false;
			T parentId = entry.getKey();
			
			for (TreeNode<T> treeNode : treeNodes) {
				//找到父节点，把节点挂在子节点下
				if (parentId != null && parentId.equals(treeNode.getId())) {
					treeNode.setChildren(entry.getValue());
					hasFindParent = true;
					break;
				}
			}
			
			//如果未找到父节点，直接就是父节点
			if (!hasFindParent) {
				realTree.addAll(entry.getValue());
			}
		}
	}

	/**
	 * 按照id分组 
	 * @param groupMap
	 */
	private void groupByParentId(Map<T, List<TreeNode<T>>> groupMap) {
		T parentId;
		//按照id分组
		for (TreeNode<T> treeNode : treeNodes) {
			parentId = treeNode.getParentId();
			List<TreeNode<T>> list = groupMap.get(parentId);
			if (list == null) {
				list = new ArrayList<>();
				groupMap.put(parentId, list);
			}
			list.add(treeNode);
		}
	}

	/**
	 * @return 返回构造好树后的数据
	 */
	public List<TreeNode<T>> getRealTree() {
		return realTree;
	}
}
