resource "aws_ecr_repository" "repo_franchise_api" {
  name                 = var.ecr_repository_name
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Environment = var.environment
    Project     = var.project_name
  }
}

output "ecr_repository_url" {
  value = aws_ecr_repository.repo_franchise_api.repository_url
}