package cn.bizspring.cloud.goods.admin.handler;

import cn.bizspring.cloud.common.core.util.JsonUtils;
import cn.bizspring.cloud.goods.common.entity.SpecificationValue;
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
public class SpecificationValueConverter extends BaseTypeHandler <List<SpecificationValue>> {
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<SpecificationValue> parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(i, JsonUtils.toJson(parameter));
		}
		
	}
	
	@Override
	public List<SpecificationValue> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String dbData = rs.getString(columnName);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<SpecificationValue>>() { });
	}

	@Override
	public List<SpecificationValue> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String dbData = rs.getString(columnIndex);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<SpecificationValue>>() { });
	}

	@Override
	public List<SpecificationValue> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String dbData = cs.getString(columnIndex);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<SpecificationValue>>() { });
	}
	
}
