import graph.algo.CommunityInfo;
import graph.algo.LouvainCalculator;
import graph.entity.Graph;
import graph.entity.Link;
import org.junit.Test;

import java.util.Arrays;

public class MyTest {

    /**
     * 单层划分
     */
    @Test
    public void testSingle() {
        Graph g = new Graph();
        // 0->1->2->0
        g.addLinks(Arrays.asList(new Link(0, 1, 1.0)));
        g.addLinks(Arrays.asList(new Link(1, 2, 1.0)));
        g.addLinks(Arrays.asList(new Link(2, 0, 1.0)));
        // 3->4->5->3
        g.addLinks(Arrays.asList(new Link(3, 4, 1.0)));
        g.addLinks(Arrays.asList(new Link(4, 5, 1.0)));
        g.addLinks(Arrays.asList(new Link(5, 3, 1.0)));

        LouvainCalculator louvainCalculator = new LouvainCalculator(g);// 构造计算器
        CommunityInfo communityInfo = louvainCalculator.findCommunitiesSingleLevel();// 执行划分
        System.out.println(communityInfo);// 输出结果
    }

    /**
     * 多层划分
     */
    @Test
    public void testMultiple() {
        Graph g = new Graph();
        // 0->1->2->0
        g.addLinks(Arrays.asList(new Link(0, 1, 1.0)));
        g.addLinks(Arrays.asList(new Link(1, 2, 1.0)));
        g.addLinks(Arrays.asList(new Link(2, 0, 1.0)));
        // 3->4->5->3
        g.addLinks(Arrays.asList(new Link(3, 4, 1.0)));
        g.addLinks(Arrays.asList(new Link(4, 5, 1.0)));
        g.addLinks(Arrays.asList(new Link(5, 3, 1.0)));
        // 6->7->8->6->5
        g.addLinks(Arrays.asList(new Link(6, 7, 1.0)));
        g.addLinks(Arrays.asList(new Link(7, 8, 1.0)));
        g.addLinks(Arrays.asList(new Link(8, 9, 1.0)));
        g.addLinks(Arrays.asList(new Link(9, 6, 1.0)));
        g.addLinks(Arrays.asList(new Link(6, 8, 1.0)));
        g.addLinks(Arrays.asList(new Link(7, 9, 1.0)));
        g.addLinks(Arrays.asList(new Link(6, 5, 1.0)));

        LouvainCalculator louvainCalculator = new LouvainCalculator(g);// 构造计算器
        CommunityInfo communityInfo = louvainCalculator.findCommunitiesMultiLevel(2);// 执行划分
        System.out.println(communityInfo);// 输出结果
    }

}
