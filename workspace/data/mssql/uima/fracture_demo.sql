update $(db_schema).fracture_demo set note_text = replace(note_text, '<br/>', CHAR(10));