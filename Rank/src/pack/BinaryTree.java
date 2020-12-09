package pack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pack.BinaryTree.TreeNode;

public class BinaryTree {
	/*
	 * �ڲ��࣬���ڵ�
	 */
	public static class TreeNode {
	    int val = 0;//�ڵ��ֵ
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
    
	//�õ�������
	//			   1
	//		 2		      3
	//	  4     5      6     7
	//   8 9
    public static List<TreeNode> createBinTree(int[] array) {
        List<TreeNode> nodeList = new LinkedList<TreeNode>();  
        // ��һ�������ֵ����ת��ΪNode�ڵ�  
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {  
            nodeList.add(new TreeNode(array[nodeIndex]));  
        }  
        // ��ǰlastParentIndex-1�����ڵ㰴�ո��ڵ��뺢�ӽڵ�����ֹ�ϵ����������  
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {  
            // ����  
            nodeList.get(parentIndex).left = nodeList.get(parentIndex * 2 + 1);  
            // �Һ���  
            nodeList.get(parentIndex).right = nodeList.get(parentIndex * 2 + 2);  
        }  
        // ���һ�����ڵ�:��Ϊ���һ�����ڵ����û���Һ��ӣ����Ե����ó�������  
        int lastParentIndex = array.length / 2 - 1;  
        // ����  
        nodeList.get(lastParentIndex).left = nodeList.get(lastParentIndex * 2 + 1);  
        // �Һ���,�������ĳ���Ϊ�����Ž����Һ���  
        if (array.length % 2 == 1) {  
            nodeList.get(lastParentIndex).right = nodeList.get(lastParentIndex * 2 + 2);  
        }  
        return nodeList;
    }  
    
    //����������������
    public static void main(String[] args) {
    	//����������
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };  
		List<TreeNode> nodeList = createBinTree(array);
		
		//��������
		System.out.println("traverse���������");
		Solution1 s1=new Solution1();
		ArrayList<Integer> t1=s1.preorderTraverse(nodeList.get(0));
		for(int i=0;i<t1.size();i++) {
			System.out.print(t1.get(i)+" ");
		}
		
		System.out.println("\n���η����������");
		Solution2 s2=new Solution2();
		ArrayList<Integer> t2=s2.preorderDivCon(nodeList.get(0));
		for(int i=0;i<t2.size();i++) {
			System.out.print(t2.get(i)+" ");
		}
		
		System.out.println("\n����Ѱ��ȫ��·����");
		AllPath1 a1=new AllPath1();
		ArrayList<String> t3=(ArrayList<String>) a1.binaryTreePaths(nodeList.get(0));
		for(int i=0;i<t3.size();i++) {
			System.out.print(t3.get(i)+"\n");
		}
		
		System.out.println("\n���η�Ѱ��ȫ��·����");
		AllPath2 a2=new AllPath2();
		ArrayList<String> t4=(ArrayList<String>) a2.DivConPaths(nodeList.get(0));
		for(int i=0;i<t4.size();i++) {
			System.out.print(t4.get(i)+"\n");
		}
		
		System.out.println("\nѰ����С�Ͷ�Ӧ�Ľڵ㣺");
		FindMinSumRoot f=new FindMinSumRoot();
		TreeNode node=f.findSubtree(nodeList.get(0));
		System.out.print(node.val);
	}

}
