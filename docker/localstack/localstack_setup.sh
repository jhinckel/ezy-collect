#!/usr/bin/env bash
 
set -euo pipefail

# enable debug
# set -x

echo "configuring SQS/SNS"
echo "==================="

LOCALSTACK_HOST=localhost
AWS_REGION=sa-east-1

create_sqs_queue() {
  local QUEUE_NAME=$1
  echo "Creating SQS queue '${QUEUE_NAME}'"
  awslocal --endpoint-url http://${LOCALSTACK_HOST}:4566 sqs create-queue --queue-name ${QUEUE_NAME} --region ${AWS_REGION}
}

create_sns_topic() {
  local TOPIC_NAME=$1
  echo "Creating SNS topic '${TOPIC_NAME}'"
  awslocal sns create-topic --endpoint-url http://${LOCALSTACK_HOST}:4566 --name ${TOPIC_NAME}
}

create_sqs_queue "payment-notification"

echo "######################################"
echo "########### Listing queues ###########"
aws --endpoint-url=http://${LOCALSTACK_HOST}:4566 sqs list-queues
echo "######################################"
echo "########### Listing topics ###########"
aws --endpoint-url=http://${LOCALSTACK_HOST}:4566 sns list-topics
echo "######################################"

