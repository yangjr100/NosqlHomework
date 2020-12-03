package pack;

public class TreeNode {
    int val = 0;//节点的值
    TreeNode left = null;
    TreeNode right = null;
    public TreeNode() {
	}
    public TreeNode(int val) {
        this.val = val;
    }
	public TreeNode(int val,TreeNode leftChild,TreeNode rightChild) {
		this.val = val;
		this.left = leftChild;
		this.right = rightChild;
	}

}
