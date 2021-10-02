package me.dwliu.framework.common.node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类，如：菜单、部门等
 *
 * @author eric
 * @since 1.0.0
 */
public class TreeUtils {

	/**
	 * 根据pid，构建树节点
	 *
	 * @param treeNodes
	 * @param pid
	 * @param <T>
	 * @return
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes, Long pid) {
		//pid不能为空
//		AssertUtils.isNull(pid, "pid");

		List<T> treeList = new ArrayList<>();
		for (T treeNode : treeNodes) {
			if (pid.equals(treeNode.getPid())) {
				treeList.add(findChildren(treeNodes, treeNode));
			}
		}

		return treeList;
	}

	/**
	 * 查找子节点
	 */
	private static <T extends TreeNode> T findChildren(List<T> treeNodes, T rootNode) {
		for (T treeNode : treeNodes) {
			if (rootNode.getId().equals(treeNode.getPid())) {
				rootNode.getChildren().add(findChildren(treeNodes, treeNode));
			}
		}
		return rootNode;
	}

	/**
	 * 构建树节点
	 *
	 * @param treeNodes
	 * @param <T>
	 * @return
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes) {
		return build(treeNodes, false);
	}

	/**
	 * 构建树节点
	 *
	 * @param treeNodes
	 * @param childrenNull 是否将末端叶子节点为空的对象设置为null ,默认是children=[]
	 * @param <T>
	 * @return
	 */
	public static <T extends TreeNode> List<T> build(List<T> treeNodes, boolean childrenNull) {

		List<T> result = new ArrayList<>();
		//list转map
		Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
		for (T treeNode : treeNodes) {
			nodeMap.put(treeNode.getId(), treeNode);
		}
//        for (T node : nodeMap.values()) {
//            T parent = nodeMap.get(node.getPid());
//            if (parent != null && !(node.getId().equals(parent.getId()))) {
//                parent.getChildren().add(node);
//                continue;
//            }
//
//            result.add(node);
//        }
		genNodeList(result, nodeMap);

		if (childrenNull) {
			treeChildrenSetNull(result);
		}
		return result;
	}

	/**
	 * 构建条件树节点
	 *
	 * @param treeNodes       全部树节点
	 * @param searchTreeNodes 搜索的树节点
	 * @param <T>
	 * @return
	 */
	public static <T extends TreeNode> List<T> buildBySearch(List<T> treeNodes, List<T> searchTreeNodes) {
		return buildBySearch(treeNodes, searchTreeNodes, false);
	}


	/**
	 * 构建条件树节点
	 *
	 * @param treeNodes       全部树节点
	 * @param searchTreeNodes 搜索的树节点
	 * @param childrenNull    是否将末端叶子节点为空的对象设置为null ,默认是children=[]
	 * @param <T>
	 * @return
	 */
	public static <T extends TreeNode> List<T> buildBySearch(List<T> treeNodes,
	                                                         List<T> searchTreeNodes,
	                                                         boolean childrenNull) {
		List<T> result = new ArrayList<>();

		//list转map
		Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
		Map<Long, T> nodeMapNew = new LinkedHashMap<>(treeNodes.size());
		for (T treeNode : treeNodes) {
			nodeMap.put(treeNode.getId(), treeNode);
		}

		for (T searchTreeNode : searchTreeNodes) {
			nodeMapNew.put(searchTreeNode.getId(), searchTreeNode);
			getPidNode(nodeMap, nodeMapNew, searchTreeNode);
		}

		genNodeList(result, nodeMapNew);

		if (childrenNull) {
			treeChildrenSetNull(result);
		}

		return result;
	}

	private static <T extends TreeNode> void genNodeList(List<T> result, Map<Long, T> nodeMapNew) {
		for (T node : nodeMapNew.values()) {
			T parent = nodeMapNew.get(node.getPid());
			if (parent != null && !(node.getId().equals(parent.getId()))) {
				parent.getChildren().add(node);
				continue;
			}

			result.add(node);
		}
	}

	private static <T extends TreeNode> void getPidNode(Map<Long, T> nodeMap, Map<Long, T> nodeMapNew, T searchTreeNode) {
		if (searchTreeNode.getPid() != null) {
			T t = nodeMap.get(searchTreeNode.getPid());
			if (t == null) {
				return;
			}
			nodeMapNew.put(searchTreeNode.getPid(), t);
//            if (t.getPid() != null || t.getPid() == 0) {
//                T t1 = nodeMap.get(t.getPid());
//                nodeMapNew.put(searchTreeNode.getPid(), t1);
//            }

			getPidNode(nodeMap, nodeMapNew, t);
		}
	}


	/**
	 * 表格树形数据展现时当children为空数组设置为null
	 *
	 * @param list 树形集合
	 */
	private static <T extends TreeNode> void treeChildrenSetNull(List<T> list) {
		for (T p : list) {
			if (p.getChildren() != null && p.getChildren().isEmpty()) {
				p.setChildren(null);
				continue;
			}

			treeChildrenSetNull(p.getChildren());
		}
	}


}
