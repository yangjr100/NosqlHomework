package pack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pack.BinaryTree.TreeNode;

public class BinaryTree {
	/*
	 * 内部类，树节点
	 */
	public static class TreeNode {
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
		public TreeNode getLeft() {
			return left;
		}
		public TreeNode getRight() {
			return right;
		}
	}
    
	//得到的树：
	//			   1
	//		 2		      3
	//	  4     5      6     7
	//   8 9
    public static List<TreeNode> createBinTree(int[] array) {
        List<TreeNode> nodeList = new LinkedList<TreeNode>();  
        // 将一个数组的值依次转换为Node节点  
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {  
            nodeList.add(new TreeNode(array[nodeIndex]));  
        }  
        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树  
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {  
            // 左孩子  
            nodeList.get(parentIndex).left = nodeList.get(parentIndex * 2 + 1);  
            // 右孩子  
            nodeList.get(parentIndex).right = nodeList.get(parentIndex * 2 + 2);  
        }  
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理  
        int lastParentIndex = array.length / 2 - 1;  
        // 左孩子  
        nodeList.get(lastParentIndex).left = nodeList.get(lastParentIndex * 2 + 1);  
        // 右孩子,如果数组的长度为奇数才建立右孩子  
        if (array.length % 2 == 1) {  
            nodeList.get(lastParentIndex).right = nodeList.get(lastParentIndex * 2 + 2);  
        }  
        return nodeList;
    }  
    
    //创建二叉树并测试
    public static void main(String[] args) {
    	//创建二叉树
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };  
		List<TreeNode> nodeList = createBinTree(array);
		
		//方法测试
		System.out.println("traverse先序遍历：");
		Solution1 s1=new Solution1();
		ArrayList<Integer> t1=s1.preorderTraverse(nodeList.get(0));
		for(int i=0;i<t1.size();i++) {
			System.out.print(t1.get(i)+" ");
		}
		
		System.out.println("\n分治法先序遍历：");
		Solution2 s2=new Solution2();
		ArrayList<Integer> t2=s2.preorderDivCon(nodeList.get(0));
		for(int i=0;i<t2.size();i++) {
			System.out.print(t2.get(i)+" ");
		}
		
		System.out.println("\n遍历寻找全部路径：");
		AllPath1 a1=new AllPath1();
		ArrayList<String> t3=(ArrayList<String>) a1.binaryTreePaths(nodeList.get(0));
		for(int i=0;i<t3.size();i++) {
			System.out.print(t3.get(i)+"\n");
		}
		
		System.out.println("\n分治法寻找全部路径：");
		AllPath2 a2=new AllPath2();
		ArrayList<String> t4=(ArrayList<String>) a2.DivConPaths(nodeList.get(0));
		for(int i=0;i<t4.size();i++) {
			System.out.print(t4.get(i)+"\n");
		}
		
		System.out.println("\n寻找最小和对应的节点：");
		FindMinSumRoot f=new FindMinSumRoot();
		TreeNode node=f.findSubtree(nodeList.get(0));
		System.out.print(node.val);
	}

}
