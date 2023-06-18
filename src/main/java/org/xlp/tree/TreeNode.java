package org.xlp.tree;

import java.util.List;

import org.xlp.utils.collection.XLPCollectionUtil;

/**
 * <p>创建时间：2023年6月14日 下午11:03:27</p>
 * @author xlp
 * @version 1.0 
 * @Description 树对象顶级接口
*/
public interface TreeNode<T> {
	/**
	 * 设置父节点id
	 */
	void setParentId(T parentId);
	
	/**
	 * 获取父节点id
	 */
	T getParentId();
	
	/**
	 * 获取本身唯一标识
	 */
	T getId();
	
	/**
	 * 设置本身唯一标识
	 */
	void setId(T id);
	
	/**
	 * 判断是否有子节点
	 * @return
	 */
	default boolean hasChildren(){
		return !XLPCollectionUtil.isEmpty(getChildren());
	}
	
	/**
	 * 获取父节点
	 * @return
	 */
	default TreeNode<T> getParent(){
		return null;
	}
	
	/**
	 * 设置父节点
	 * @param treeNode
	 */
	default void setParent(TreeNode<T> treeNode){}
	
	/**
	 * 获取孩子节点
	 * @return
	 */
	List<TreeNode<T>> getChildren();
	
	/**
	 * 设置子节点
	 * @param treeNodes
	 */
	void setChildren(List<TreeNode<T>> treeNodes);
	
	/**
	 * 添加子节点
	 * @param treeNode
	 * @return
	 */
	boolean addChild(TreeNode<T> treeNode);
}
