package apps.healthmanage.views;

import apps.healthmanage.views.service.IPageService;
import apps.healthmanage.views.service.impl.AddReport;
import apps.healthmanage.views.service.impl.HealthStatistics;
import apps.healthmanage.views.service.impl.UserPage;

/**
 * 页面创建工厂
 * @author huhailong
 *
 */
public class PageFactory {

	public static IPageService createPageService(String itemName) {
		IPageService pageService = null;
		switch(itemName) {
			case "个人中心":
				pageService = new UserPage();
				break;
			case "健康统计":
				pageService = new HealthStatistics();
				break;
			case "增加记录":
				pageService = new AddReport();
				break;
			default:
				pageService = new UserPage();
				break;
		}
		return pageService;
	}
}
