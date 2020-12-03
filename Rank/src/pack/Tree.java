package pack;

import java.util.ArrayList;
import java.util.List;

/*
 * 1.������������ַ������ݹ顢����
 */
//version 1:traverse �������
class Solution1{
	public ArrayList<Integer> preorderTraverse(TreeNode root){
		ArrayList<Integer> tra =new ArrayList<>();
		traverse(root,tra);
		return tra;
	}

	//��rootΪ���ڵ��preorder������������
	private void traverse(TreeNode root, ArrayList<Integer> tra) {
		if(root==null) {
			return;
		}
		tra.add(root.val);//��
		traverse(root.left,tra);//��
		traverse(root.right,tra);//��
	}
}

//version 2:divide and conquer���η� �������
class Solution2{
	//���ݹ�ĺ������壬�Ӹ���㿪ʼ������������õ����ӵ����������������õ��Һ��ӵ�������������Ȼ�����������˳�����merge
	public ArrayList<Integer> preorderDivCon(TreeNode root){
		ArrayList<Integer> dc =new ArrayList<>();
		
		//null or leaf
		if(root==null) {
			return dc;
		}
	
		//divide
		ArrayList<Integer> left = preorderDivCon(root.left);//��������
		ArrayList<Integer> right = preorderDivCon(root.right);//�����Һ���
		
		//conquer
		dc.add(root.val);//�ȼ��Լ�
		dc.addAll(left);//�ټ�����
		dc.addAll(right);//�ټ��Һ���
		return dc;
	}
}
	
/*
 * 2.�ҵ��������е�����·���������������η�
 */
//version 1:������
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

//version 2:���η�
//�ȶ���һ�������������κ�һ���ڵ㣬�Ѵ�����ڵ㵽Ҷ�ӽڵ��·�������
//�ֱ��������������ȥ�������Ҷ��������ˣ��ͰѸ��ڵ�ֱ�ŵ����Һ��ӵĽ�����һ����ͷ������
class AllPath2{
	public List<String> DivConPaths(TreeNode root){
		List<String> paths = new ArrayList<String>();
		if(root == null) {
			return paths;
		}
		//divide
		List<String> leftPaths=DivConPaths(root.left);//�����ӵ�·��
		List<String> rightPaths=DivConPaths(root.right);//���Һ��ӵ�·��
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
 * 3.��ÿ���ڵ��������˽ڵ��Լ������ӽڵ�ĺͣ����غ���С��
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





