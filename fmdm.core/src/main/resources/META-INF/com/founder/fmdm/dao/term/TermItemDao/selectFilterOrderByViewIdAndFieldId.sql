select field_order from VIEW_FIELD
where 1=1
/*%if viewId != null && viewId != "" */
	and view_id = /*viewId*/'8bf5caea20ea42aaadd80f76ff946120' 
/*%end */
/*%if fieldId != null && fieldId != "" */
	and field_id =/*fieldId*/'6380ea8beee7482686bf76750ed0ec6b'
/*%end */