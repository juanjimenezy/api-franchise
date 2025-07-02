variable "ecr_repository_name" {
  description = "Nombre del repositorio ECR"
  type        = string
  default     = "mi-repo-ecr"
}

variable "environment" {
  description = "Ambiente (dev, qa, prod, etc)"
  type        = string
  default     = "dev"
}

variable "project_name" {
  description = "Nombre del proyecto"
  type        = string
  default     = "proyecto-demo"
}