
package com.nms.util.beans;

public class ColumnDefinition
{
	private String profile;
	private String columnName;
	private String htmlColumnName;
	private String colDescription;
	private boolean defaultColumn;
	private String dataType;
	private boolean visible;
	private int width;

	public final static String NUMERIC = "number";
	public final static String TEXT = "text";

	public ColumnDefinition()
	{
		super();
	}

	public ColumnDefinition( String aColumnID, String aColumnName )
	{
		this( aColumnID, aColumnName, ColumnDefinition.TEXT );
	}

	public ColumnDefinition( String aColumnID, String aColumnName, String aDataType )
	{
		setColumnName( aColumnID );
		setHtmlColumnName( aColumnName );
		setDataType( aDataType );
		setVisible( true );
		setDefaultColumn( true );
	}

	public ColumnDefinition( String aColumnID, String aColumnName, String aDataType, int aWidth )
	{
		this(aColumnID, aColumnName, aDataType);
		setWidth(aWidth);
	}

	public String getDataType()
	{
		return dataType;
	}

	public void setDataType( String dataType )
	{
		this.dataType = dataType;
	}

	public String getProfile()
	{
		return profile;
	}

	public void setProfile( String profile )
	{
		this.profile = profile;
	}

	public String getColumnName()
	{
		return columnName;
	}

	public void setColumnName( String columnID )
	{
		this.columnName = columnID;
	}

	public String getHtmlColumnName()
	{
		return htmlColumnName;
	}

	public void setHtmlColumnName( String columnName )
	{
		this.htmlColumnName = columnName;
	}

	public String getColumnDescription()
	{
		return colDescription;
	}

	public void setColumnDescription( String colDesc )
	{
		if( colDesc == null )
			colDesc = "";
		this.colDescription = colDesc.trim();
	}

	public boolean isDefaultColumn()
	{
		return defaultColumn;
	}

	public void setDefaultColumn( boolean defaultColumn )
	{
		this.defaultColumn = defaultColumn;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible( boolean visible )
	{
		this.visible = visible;
	}

	public String toString()
	{
		return getColumnName() + ", " + getHtmlColumnName();
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth( int width )
	{
		this.width = width;
	}
}
