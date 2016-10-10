#!/bin/bash

echo "Starting to run CloudFormation template"

echo -n "Enter stack name and press [ENTER]: "
read stack
echo -n "Enter username and press [ENTER]: "
read username
echo -n "Enter password and press [ENTER]: "
read password

aws cloudformation validate-template --template-body file://create_rds_db.json

STACK_ID=$(aws cloudformation create-stack --stack-name $stack --template-body file://create_rds_db.json --parameters ParameterKey=DBUser,ParameterValue=$username ParameterKey=DBPassword,ParameterValue=$password | jq '.StackId')

echo "Creating RDS Postgres database in AWS..."
