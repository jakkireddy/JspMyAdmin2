/**
 * 
 */
package com.jspmyadmin.framework.tag.jma;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.jspmyadmin.framework.tag.support.AbstractSimpleTagSupport;
import com.jspmyadmin.framework.util.FrameworkConstants;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/01/28
 *
 */
public class VarTag extends AbstractSimpleTagSupport {

	private String name = null;
	private String value = null;
	private String scope = null;

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) super.getJspContext();
		if (value.startsWith(FrameworkConstants.SYMBOL_HASH)) {
			Object temp = null;
			if (scope == null || FrameworkConstants.PAGE.equals(scope)) {
				temp = pageContext.getAttribute(value.substring(1));
			} else if (FrameworkConstants.COMMAND.equals(scope)) {
				temp = pageContext.getRequest().getAttribute(
						FrameworkConstants.COMMAND);
				temp = super.getReflectValue(temp, value.substring(1));
			} else if (FrameworkConstants.REQUEST.equals(scope)) {
				temp = pageContext.getRequest()
						.getAttribute(value.substring(1));
			}
			if (temp != null) {
				pageContext.setAttribute(name, temp, PageContext.PAGE_SCOPE);
			}
		} else {
			pageContext.setAttribute(name, value, PageContext.PAGE_SCOPE);
		}
	}
}