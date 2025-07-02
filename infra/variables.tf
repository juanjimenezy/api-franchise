variable "db_password" {
  description = "Admin password for RDS postgres"
  type        = string
}

variable "ecr_repository_name" {
  description = "Name of the ECR repository"
  type        = string
}

variable "environment" {
  description = "Environment (dev, qa, prod, etc)"
  type        = string
  default     = "dev"
}

variable "project_name" {
  description = "Nombre del proyecto"
  type        = string
  default     = "proyecto-demo"
}

variable "ecs_task_execution_role_name" {
  description = "Nombre del rol de ejecución de tareas de ECS"
  type        = string
  default     = "ecsTaskExecutionRole"
}