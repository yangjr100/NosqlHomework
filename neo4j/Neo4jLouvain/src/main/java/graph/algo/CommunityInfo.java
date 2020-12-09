package graph.algo;

import java.util.Arrays;

/**
 * 社区信息
 */
public class CommunityInfo {
    public int communitiesNo;//节点编号
    public int[] nodeCommunityNo;//i代表节点编号，value代表社区编号
    public int[][] communityNodeIds;//i代表社区编号，j代表节点编号

    @Override
    public String toString()
    {
        return "CommunityInfo{" +
                "communitiesNo=" + communitiesNo +
                ", nodeCommunityNo=" + Arrays.toString(nodeCommunityNo) +
                ", communityNodeIds=" + Arrays.deepToString(communityNodeIds) +
                '}';
    }

}
