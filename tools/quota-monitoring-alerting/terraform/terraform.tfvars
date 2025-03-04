/*
Copyright 20210 Google LLC

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
project_id                    = "quota-monitoring-project-id"
region                        = "us-west3"
service_account_email         = "sa-quota-monitoring-project-id@quota-monitoring-project-id.iam.gserviceaccount.com"
source_code_bucket_name       = "quota-monitoring-solution-source"
source_code_zip               = "quota-monitoring-solution.zip"
source_code_notification_zip  = "quota-monitoring-notification.zip"
dataflow_job_temp_storage     = "dataflow-temp-quota-monitoring-project-20"
scheduler_cron_job_frequency  = "0 0 * * *"
Alert_data_scanning_frequency = "every 12 hours"
folders                       = "[38659473572,38659473573]"
organizations                 = "[172338721810,172338721811]"
threshold                     = "80"
fromEmailId                   = "infosec@democorp.com"
toEmailIds                    = "infosec@democorp.com,to.someone@democorp.com"
SENDGRID_API_KEY              = "JF8JZzBiZIBQx8L68pK" //Consider using Environment Variables for this sensitive info