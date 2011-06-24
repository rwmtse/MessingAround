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
package blackberry.ui.event;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.Scriptable;
import net.rim.device.api.ui.ScrollChangeListener;
import net.rim.device.api.ui.Manager;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import blackberry.ui.event.FunctionOnScrollStart;
import blackberry.ui.event.FunctionOnScrollEnd;

public class EventNamespace extends Scriptable {
	private static final String FIELD_ONSCROLL_START = FunctionOnScrollStart.NAME;
	private static final String FIELD_ONSCROLL_END = FunctionOnScrollEnd.NAME;
	public static final String NAME = "blackberry.ui.scrollEvent"; 
	
	private FunctionOnScrollStart _onScrollStartFunction = null;
	private FunctionOnScrollEnd _onScrollEndFunction = null;
	private EventListener _eventListener = null;
	private BrowserField _browserField = null;
	
	
	public EventNamespace(BrowserField browserField) {
		_browserField = browserField;
		
		// Create our event functions
		_onScrollStartFunction = new FunctionOnScrollStart();
		_onScrollEndFunction = new FunctionOnScrollEnd();
		
		// Create our Manager event listener for scrolling
		_eventListener = new EventListener(_onScrollStartFunction, _onScrollEndFunction);
		_browserField.getManager().setScrollListener(_eventListener);	
	}

    public Object getField(String name) throws Exception {
        if (name.equals(FIELD_ONSCROLL_START)) {
            return _onScrollStartFunction;
        }
		else if (name.equals(FIELD_ONSCROLL_END)) {
            return _onScrollEndFunction;
        }
        return super.getField(name);
    }

    public boolean putField(String field, Object value) throws Exception {
        return super.putField(field, value);
    }
	
	
	/**
	 * Listens for all of the scrolling events on the manager
	 * 
	 * @author tneil
	 * 
	 */
	private class EventListener implements ScrollChangeListener {
		private static final long TIMEOUT = 300;
		
		private FunctionOnScrollStart _onScrollStartFunction = null;
		private FunctionOnScrollEnd _onScrollEndFunction = null;
		private Timer _timer = new Timer();
		private Date _lastScroll = null;
		private Manager _manager = null;
		
		private int _reset = 0; 
		
		public EventListener(FunctionOnScrollStart onScrollStartFunction,  FunctionOnScrollEnd onScrollEndFunction) {
			_onScrollStartFunction = onScrollStartFunction;
			_onScrollEndFunction = onScrollEndFunction;
		}
		
		public void scrollChanged(Manager manager, int newHorizontalScroll, int newVerticalScroll) {
			
			_manager = manager;
			if ( _reset == 0) {
				_reset++;
				_onScrollStartFunction.trigger();
			}
			// Record our last time of scroll
			_lastScroll = new Date();
			
			// Create our timeout task
			ScrollTimerTask _timerTask = new ScrollTimerTask(this);
			_timer.schedule(_timerTask, TIMEOUT);	
		}
		
		public void scrollEnd() {
			Date now = new Date();
			if (now.getTime() - _lastScroll.getTime() >= TIMEOUT) {
				_onScrollEndFunction.trigger(_manager.getVerticalScroll(), _manager.getHorizontalScroll());
				_reset = 0;
			}
		}
	}
	
	/**
	 * Provides our countdown task to fire on scroll end
	 * 
	 * @author tneil
	 * 
	 */
	private class ScrollTimerTask extends TimerTask {
		private EventListener _eventListener = null;
	
	
		public ScrollTimerTask(EventListener eventListener) {
			_eventListener = eventListener;		
		}
		
		public void run() {
			_eventListener.scrollEnd();
		}
	
	}
}

