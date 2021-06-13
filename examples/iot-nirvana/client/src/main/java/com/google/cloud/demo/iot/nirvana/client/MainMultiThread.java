/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.demo.iot.nirvana.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.common.base.Throwables;

/** MainMultiThread class of the device simulator */
public class MainMultiThread {

  static final Logging LOGGER = LoggingOptions.getDefaultInstance().getService();
  private static final String NO_ID = "no_id";
  private static final int MYTHREADS = 193;

  public static void main(String args[]) {
	  ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
	  String k1 = args[7];
	  String k2 = args[9];
	  for(int i=0; i < MYTHREADS; i++) {
		  try {
			  args[7] = k1 + i + ".pem";
			  args[9] = k2 + i + "_pkcs8";
			  args[11] = ""+i;
			  
			  // Read input parameters
			  final ClientOptions options = ClientOptions.newBuilder().build();
			  options.parse(args);
			  Runnable worker =  new Runnable() {
					@Override
					public void run() {
						IotDeviceMgr deviceMgr = null;
						IotDevice device = null;
						try {
						      // Create the device manager
						      deviceMgr =
						          IotDeviceMgr.newBuilder()
						              .setGcpProjectId(options.getGcpProjectId())
						              .setGcpRegion(options.getGcpRegion())
						              .setRegistryName(options.getRegistryName())
						              .build();
	
						      
						      // Create the device and register it in the device registry
						      device =
						          deviceMgr.newRegisteredDevice(
						              options.getRsaCertificateFilePath(), options.getPrivateKey(), options.getCityIndex());
	
						      // Start publishing message
						      device.publish();
	
						      // Start publishing messages to Cloud IoT Core
						      device.publish();
						    } catch (ClientException ce) {
						    	ce.printStackTrace();
						    	try {
							        LogUtils.logError(
							            LOGGER,
							            NO_ID,
							            options.getGcpProjectId(),
							            String.format("The client exception retry. %s", Throwables.getStackTraceAsString(ce)));
							      } catch (Exception ex) {
							    	  ex.printStackTrace();
							      }
						    	
						    	try {
						    		Thread.sleep(30000);
						    		
						    		device.publish();
						    		
							      } catch (Exception ef) {
							    	  try {
									        LogUtils.logError(
									            LOGGER,
									            NO_ID,
									            options.getGcpProjectId(),
									            String.format("The client exception retry. %s", Throwables.getStackTraceAsString(ef)));
									      } catch (Exception ee) {
									    	  ee.printStackTrace();
									      }
							      }
						    	
						    } catch (Exception e) {
						    	e.printStackTrace();
						      try {
						        LogUtils.logError(
						            LOGGER,
						            NO_ID,
						            options.getGcpProjectId(),
						            String.format("Exiting main program. Cause %s", Throwables.getStackTraceAsString(e)));
						      } catch (Exception ex) {
						        // Nothing to do here
						    	  ex.printStackTrace();
						      }
	//					      System.exit(STATUS_ERR);
						    }
						
					}
				};
			  executor.execute(worker);
		  } catch (Exception e) {
			  
		  }
	  }
	  
	executor.shutdown();
	// Wait until all threads are finish
	while (!executor.isTerminated()) {
	
	}
	System.out.println("\nFinished all threads");
  }
}
