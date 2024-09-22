package cn.bizspring.cloud.common.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import cn.bizspring.cloud.common.datasource.constants.DataSourceConstants;
import org.jasypt.encryption.StringEncryptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 从数据源中获取 配置信息
 */
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {
	private final DruidDataSourceProperties druidDataSourceProperties;
	private final StringEncryptor stringEncryptor;

	public JdbcDynamicDataSourceProvider(StringEncryptor stringEncryptor, DruidDataSourceProperties druidDataSourceProperties) {
		super(druidDataSourceProperties.getDriverClassName(), druidDataSourceProperties.getUrl(), druidDataSourceProperties.getUsername(), druidDataSourceProperties.getPassword());
		this.stringEncryptor = stringEncryptor;
		this.druidDataSourceProperties = druidDataSourceProperties;
	}

	/**
	 * 初始数据源
	 *
	 * @param statement 语句
	 * @return 数据源参数
	 * @throws SQLException sql异常
	 */
	@Override
	protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery(DataSourceConstants.DS_QUERY_SQL);

		Map<String, DataSourceProperty> map = new HashMap<>(8);
		DataSourceProperty property = new DataSourceProperty();
		property.setUsername(druidDataSourceProperties.getUsername());
		property.setPassword(druidDataSourceProperties.getPassword());
		property.setUrl(druidDataSourceProperties.getUrl());
		map.put(DataSourceConstants.DS_MASTER, property);
		while (rs.next()) {
			String name = rs.getString(DataSourceConstants.DS_NAME);
			String username = rs.getString(DataSourceConstants.DS_USER_NAME);
			String password = rs.getString(DataSourceConstants.DS_USER_PWD);
			String url = rs.getString(DataSourceConstants.DS_JDBC_URL);
			property = new DataSourceProperty();
			property.setUsername(username);
			property.setPassword(stringEncryptor.decrypt(password));
			property.setUrl(url);
			map.put(name, property);
		}
		return map;
	}
}
