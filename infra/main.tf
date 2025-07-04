terraform {
  required_version = ">= 1.6.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
  profile = "307946667563_juanjiimenez8@gmail.com"
}

module "network" {
  source = "./modules/network"
}

module "security" {
  source  = "./modules/security"
  vpc_id  = module.network.vpc_id
}

module "database" {
  source      = "./modules/rds"
  db_password = var.db_password
  subnet_ids  = module.network.subnet_ids
  sg_id       = module.security.rds_sg_id
}

module "ecr" {
  source = "./modules/ecr"
  ecr_repository_name = var.ecr_repository_name
  environment = var.environment
  project_name = var.project_name
}

module "iam" {
  source = "./modules/iam"
  iam_task_execution_role_name = var.ecs_task_execution_role_name
}

module "ecs" {
  subnet_ids          = module.network.subnet_ids
  ecs_sg_id           = module.security.ecs_sg_id
  vpc_id              = module.network.vpc_id
  source              = "./modules/ecs"
  rds_endpoint        = module.database.rds_endpoint
  db_password         = var.db_password
  execution_role_arn  = module.iam.ecs_task_execution_role_arn
  ecr_repository_url  = module.ecr.ecr_repository_url
}

output "ecr_repository_url" {
  value = module.ecr.ecr_repository_url
}

output "rds_endpoint" {
  value = module.database.rds_endpoint
}

output "rds_port" {
  value = module.database.rds_port
}

output "alb_dns_name" {
  value = module.ecs.alb_dns_name
}