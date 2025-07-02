resource "aws_db_subnet_group" "db_subnets" {
  name       = "franchise-db-subnet-group"
  subnet_ids = var.subnet_ids
}

resource "aws_db_instance" "franchise_postgres" {
  identifier              = "franchise-db"
  engine                  = "postgres"
  instance_class          = "db.t3.micro"
  allocated_storage       = 20
  db_name                 = "franchise"
  username                = "postgres"
  password                = var.db_password
  db_subnet_group_name    = aws_db_subnet_group.db_subnets.name
  vpc_security_group_ids  = [var.sg_id]
  skip_final_snapshot     = true
  publicly_accessible     = true
}

output "rds_endpoint" {
  value = aws_db_instance.franchise_postgres.address
}

output "rds_port" {
  value = aws_db_instance.franchise_postgres.port
}