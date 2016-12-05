package cn.generatecode;
public class FieldModel {

	private String columnName;
	private String fieldName;

	public FieldModel(String columnName, String fieldName) {
		this.columnName = columnName;
		this.fieldName = fieldName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getUcolumnName() {
		return columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
	}
}
