package org.viesure.tests;

import org.testng.annotations.Test;
import org.viesure.base.BaseTest;
import org.viesure.page.common.Article;
import org.viesure.page.utils.Networking;

import java.util.List;

public class apiTest extends BaseTest {

    @Test
    public void callApi(){
        List<Article> articles= Networking.getDummyData();

        System.out.println(articles.size());
    }
}
