#!/bin/bash
#
# A shell script to push a new version of our CORS configuration
# to all GCS buckets.

gsutil cors set gcs-cors.json gs://north-castle-veterans-site-images
gsutil cors set gcs-cors.json gs://north-castle-veterans-site-scans
