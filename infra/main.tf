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

output "rds_endpoint" {
  value = module.database.rds_endpoint
}

output "rds_port" {
  value = module.database.rds_port
}