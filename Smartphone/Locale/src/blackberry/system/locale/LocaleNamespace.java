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
package blackberry.system.locale;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.Scriptable;
import net.rim.device.api.ui.ScrollChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.VirtualKeyboard;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import blackberry.system.locale.FunctionDeviceLocale;
import blackberry.system.locale.FunctionAppLocale;

public class LocaleNamespace extends Scriptable {

	private static final String FIELD_DEVICELOCALE = FunctionDeviceLocale.NAME;
	private static final String FIELD_APPLOCALE = FunctionAppLocale.NAME;
	public static final String NAME = "blackberry.system.locale"; 
	private FunctionDeviceLocale _deviceLocale = null;
	private FunctionAppLocale _appLocale = null;
	
	public LocaleNamespace() {
		
		// Create our functions
		_deviceLocale = new FunctionDeviceLocale();
		_appLocale = new FunctionAppLocale();
		
		
		
	}

    public Object getField(String name) throws Exception {
        if (name.equals(FIELD_DEVICELOCALE)) {
            return _deviceLocale;
        }
		else if (name.equals(FIELD_APPLOCALE)) {
            return _appLocale;
        }
		
		
        return super.getField(name);
    }

    public boolean putField(String field, Object value) throws Exception {
		return super.putField(field, value);
    }
	
	
	
}

