package cn.bizspring.cloud.goods.admin.handler;

import cn.bizspring.cloud.common.core.util.JsonUtils;
import cn.bizspring.cloud.goods.common.entity.SpecificationItem;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedJdbcTypes({JdbcType.LONGVARCHAR})
public class SpecificationItemConverter extends BaseTypeHandler<List<SpecificationItem>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<SpecificationItem> parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(i, JsonUtils.toJson(parameter));
		}
		
	}

	@Override
	public List<SpecificationItem> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String dbData = rs.getString(columnName);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<SpecificationItem>>() { });
	}

	@Override
	public List<SpecificationItem> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String dbData = rs.getString(columnIndex);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<SpecificationItem>>() { });
	}

	@Override
	public List<SpecificationItem> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String dbData = cs.getString(columnIndex);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<SpecificationItem>>() { });
	}


}
