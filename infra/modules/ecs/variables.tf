variable "subnet_ids" {
  description = "Subnets where the ECS service will run"
  type        = list(string)
}

variable "ecs_sg_id" {
  description = "Security group ID for ECS tasks and ALB"
  type        = string
}

variable "vpc_id" {
  description = "VPC ID for ECS and ALB resources"
  type        = string
}

variable "rds_endpoint" {
  description = "Database endpoint address"
  type        = string
}

variable "db_password" {
  description = "Database password"
  type        = string
}

variable "execution_role_arn" {
  description = "ARN of the ECS execution IAM role"
  type        = string
}

variable "ecr_repository_url" {
  description = "Name of the ECR repository"
  type        = string
}