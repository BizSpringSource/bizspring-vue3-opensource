package cn.bizspring.cloud.common.datasource.util;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@AllArgsConstructor
public class DynamicDataSourceUtils {

	private DynamicRoutingDataSource dynamicRoutingDataSource;
	private DataSourceCreator dataSourceCreator;

	/**
	 * 添加动态数据源
	 * @param dataSourceProperty
	 */
	public void addDataSource(DataSourceProperty dataSourceProperty) {
		DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
		dynamicRoutingDataSource.addDataSource(dataSourceProperty.getPoolName(), dataSource);
	}

	/**
	 * 移除动态数据源
	 * @param ds
	 */
	public void removeDataSource(String ds){
		dynamicRoutingDataSource.removeDataSource(ds);
	}

	/**
	 * 校验数据源配置是否能链接
	 * @param dataSourceProperty
	 * @return
	 * @throws SQLException
	 */
	public void checkDataSource(DataSourceProperty dataSourceProperty) {
		try {
			DriverManager.getConnection(dataSourceProperty.getUrl(),dataSourceProperty.getUsername(),dataSourceProperty.getPassword());
		}catch (SQLException e){
			log.error(e.getMessage(), e);
			throw new RuntimeException("连接失败，请检查配置信息：" + e.getMessage());
		}
	}
}
