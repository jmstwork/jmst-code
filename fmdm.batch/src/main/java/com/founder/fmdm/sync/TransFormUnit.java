package com.founder.fmdm.sync;

import org.jetel.component.DataRecordTransform;
import org.jetel.data.DataField;
import org.jetel.data.DataRecord;
import org.jetel.data.StringDataField;
import org.jetel.exception.TransformException;

public class TransFormUnit extends DataRecordTransform{

	@Override
	public int transform(DataRecord[] inputRecords, DataRecord[] outputRecords)
			throws TransformException {
		for(int i=0;i<outputRecords.length;i++){
			if(outputRecords[i] == null){
				continue;
			}
			for(int j=0;j<outputRecords[i].getNumFields();j++){
				DataField f = outputRecords[i].getField(j);
				Object v = inputRecords[i].getField(j).getValue();
				f.setValue(v);
			}
		}
        return 0;
	}
}
