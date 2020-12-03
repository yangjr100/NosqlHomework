package pack;

import java.util.ArrayList;
import java.util.List;

/*
 * 1.先序遍历的两种方法：递归、分治
 */
//version 1:traverse 先序遍历
class Solution1{
	public ArrayList<Integer> preorderTraverse(TreeNode root){
		ArrayList<Integer> tra =new ArrayList<>();
		traverse(root,tra);
		return tra;
	}

	//把root为根节点的preorder加入结果数组里
	private void traverse(TreeNode root, ArrayList<Integer> tra) {
		if(root==null) {
			return;
		}
		tra.add(root.val);//中
		traverse(root.left,tra);//左
		traverse(root.right,tra);//右
	}
}

//version 2:divide and conquer分治法 先序遍历
class Solution2{
	//做递归的函数定义，从根结点开始先序遍历，先拿到左孩子的先序遍历结果，再拿到右孩子的先序遍历结果，然后按照先序遍历顺序进行merge
	public ArrayList<Integer> preorderDivCon(TreeNode root){
		ArrayList<Integer> dc =new ArrayList<>();
		
		//null or leaf
		if(root==null) {
			return dc;
		}
	
		//divide
		ArrayList<Integer> left = preorderDivCon(root.left);//遍历左孩子
		ArrayList<Integer> right = preorderDivCon(root.right);//遍历右孩子
		
		//conquer
		dc.add(root.val);//先加自己
		dc.addAll(left);//再加左孩子
		dc.addAll(right);//再加右孩子
		return dc;
	}
}
	
/*
 * 2.找到二叉树中的所有路径：遍历法、分治法
 */
//version 1:遍历法
class AllPath1{
	public List<String> binaryTreePaths(TreeNode root){
		List<String> result = new ArrayList<String>();
		if(root == null) {
			return result;
		}
		helper(root,String.valueOf(root.val),result);
		return result;
	}

	private void helper(TreeNode root, String path, List<String> result) {
		if(root==null) {
			return;
		}
		
		if(root.left==null && root.right==null) {
			result.add(path);
			return;
		}
		if(root.left!=null) {
			helper(root.left,path+"->"+String.valueOf(root.left.val),result);
		}
		if(root.right!=null) {
			helper(root.right,path+"->"+String.valueOf(root.right.val),result);
		}
	}	
}

//version 2:分治法
//先定义一个函数，对于任何一个节点，把从这个节点到叶子节点的路径解出来
//分别从左右两个孩子去做，左右都处理完了，就把根节点分别放到左右孩子的结果里，加一个箭头就行了
class AllPath2{
	public List<String> DivConPaths(TreeNode root){
		List<String> paths = new ArrayList<String>();
		if(root == null) {
			return paths;
		}
		//divide
		List<String> leftPaths=DivConPaths(root.left);//找左孩子的路径
		List<String> rightPaths=DivConPaths(root.right);//找右孩子的路径
		//conquer
		for(String path:leftPaths) {
			paths.add(root.val+"->"+path);
		}
		for(String path:rightPaths) {
			paths.add(root.val+"->"+path);
		}
		
		//root is a leaf
		if(paths.size()==0) {
			paths.add(""+root.val);
		}
		
		return paths;
	}
}

/*
 * 3.对每个节点计算包含此节点以及所有子节点的和，返回和最小的
 */
class FindMinSumRoot{
	private int minSum;
	private TreeNode minRoot;
	public TreeNode findSubtree(TreeNode root) {
		minSum=Integer.MAX_VALUE;
		minRoot=null;
		getSum(root);
		return minRoot;
	}
	private int getSum(TreeNode root) {
		if(root==null) {
			return 0;
		}
		int sum=getSum(root.left)+getSum(root.right)+root.val;
		if(sum<minSum) {
			minSum=sum;
			minRoot=root;
		}
		
		return sum;
	}
}





