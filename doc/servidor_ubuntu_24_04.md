# Instalação no Ubuntu 24.04 (10.50.1.128)

### Definindo o local de trabalho/instalação
Pasta de trabalho:
/opt/emissorfiscal

### Criando o usuário do serviço
```bash
sudo useradd -r -s /sbin/nologin emissorfiscal
```

### Configurando como um serviço no Ubuntu.

Crie o arquivo '/etc/systemd/system/emissorfiscal.service' com o conteúdo abaixo:

```bash
[Unit]
Description=Web Service de emissão de documentos fiscais da Auto Geral
After=network.target

[Service]
User=
Group=emissorfiscal
Type=simple
WorkingDirectory=/opt/ws
ExecStart=/usr/bin/java -jar -Xmx2G /opt/emissorfiscal/emissor-fiscal-0.1.jar
Restart=always
RestartSec=45
StandardOutput=journal

[Install]
WantedBy=multi-user.target
```

### Habilitando o serviço

```bash
systemctl daemon-reload
systemctl enable emissorfiscal
systemctl start emissorfiscal
systemctl status emissorfiscal
```

