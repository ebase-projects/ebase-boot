package me.dwliu.framework.common.node;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点，所有需要实现树节点的，都需要继承该类
 *
 * @author eric
 * @since 1.0.0
 */
@Data
@Schema(description = "树形节点")
public class TreeNode<T> implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "当前节点id")
	protected Long id;
	@Schema(description = "父节点id")
	protected Long pid;
	@Schema(description = "子节点列表")
	protected List<TreeNode<T>> children = new ArrayList<>();

	public void add(TreeNode<T> node) {
		children.add(node);
	}
}
