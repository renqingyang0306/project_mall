package com.cskaoyan.project.mall;

import com.cskaoyan.project.mall.controller.mall.BrandController;
import com.cskaoyan.project.mall.controller.mall.CategoryController;
import com.cskaoyan.project.mall.controller.mall.RegionController;
import com.cskaoyan.project.mall.domain.Region;
import com.cskaoyan.project.mall.service.mall.BrandService;
import com.cskaoyan.project.mall.service.mall.RegionService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallApplicationTests {

	@Autowired
	RegionController regionController;

	@Autowired
	RegionService regionService;

	@Autowired
	BrandController brandController;

	@Autowired
	CategoryController categoryController;

	@Test
	public void contextLoads() {
		ResponseUtils responseUtils = regionController.queryAllRegion();
		System.out.println("responseUtils = " + responseUtils);
	}
	@Test
	public void selectByPid() {
		List<Region> regions = regionService.selectByPid(0);
		System.out.println("regions = " + regions);
	}

	@Test
	public void selectByPidAndType() {
		List<Region> regions = regionService.selectLikeCode("11");
		System.out.println("regions = " + regions);
	}

	@Test
	public void queryPageBrandsTest() {
		ResponseUtils responseUtils = brandController.searchBrandByIdAndName(1, 20, null,null,"add_time", "desc");
		System.out.println("responseUtils = " + responseUtils);
	}

	@Test
	public void queryAllCategoryL1Test() {
		ResponseUtils responseUtils = categoryController.queryAllCategoryL1();
		System.out.println("responseUtils = " + responseUtils);
	}

	@Test
	public void queryAllCategoryTest() {
		ResponseUtils responseUtils = categoryController.queryAllCategory();
		System.out.println("responseUtils = " + responseUtils);
	}
}
