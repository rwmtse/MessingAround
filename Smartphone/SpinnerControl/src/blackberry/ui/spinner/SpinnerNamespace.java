/*
* Copyright 2010 Research In Motion Limited.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package blackberry.ui.spinner;

import net.rim.device.api.script.Scriptable;
import blackberry.ui.spinner.FunctionOpenSpinner;

public class SpinnerNamespace extends Scriptable {
	private FunctionOpenSpinner _openFunction;
    private static final String FIELD_OPEN = FunctionOpenSpinner.NAME;
	public static final String NAME = "blackberry.ui.Spinner"; 
	
	public SpinnerNamespace() {
		_openFunction = new FunctionOpenSpinner();
	}

    public Object getField(String name) throws Exception {
        if (name.equals(FIELD_OPEN)) {
            return _openFunction;
        }
        return super.getField(name);
    }

    public boolean putField(String field, Object value) throws Exception {
        return super.putField(field, value);
    }
}