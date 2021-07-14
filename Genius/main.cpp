#include<wiringPi.h>
#include<stdio.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/socket.h>
#include<unistd.h>
#include<string.h>

#include"mcp3208.h"

#define MAXTIMINGS 83

#define TEMP 0
#define ECHO 1
#define TRIG 0
#define ECHO1 4
#define TRIG1 5
#define DHTPIN 3

int my_sock;
int serv_sock;
int clint_sock;

struct sockaddr_in serv_addr;
struct sockaddr_in clint_addr;

socklen_t clnt_addr_size;

int dht11_dat[5] = {0, };

float temp;
int illu;

char message1[1024];
char message2[1024];
char message3[1024];
char getMessage[1024];

int strLen;

float dist, s, e;
int dist1, s1, e1;

int beforeDist = 0, nowDist;

int openCount = 0;

char openIs[1024] = "open\ttrue\0";

mcp3208_t mcp3208 = {
	.ce = CEO,
};

void read_dht11_dat() {
	uint8_t laststate = HIGH;
	uint8_t counter = 0;
	uint8_t j = 0, i;
	uint8_t flag = HIGH;
	uint8_t state = 0;
	float f;

	dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

	pinMode(DHTPIN, OUTPUT);
	digitalWrite(DHTPIN, LOW);
	delay(18);

	digitalWrite(DHTPIN, HIGH);
	delayMicroseconds(30);
	pinMode(DHTPIN, INPUT);

	for (i = 0; i < MAXTIMINGS; i++) {
		counter = 0;

		while (digitalRead(DHTPIN) == laststate) {
			counter++;
			delayMicroseconds(1);
			if (counter == 200) {
				break;
			}
		}

		laststate = digitalRead(DHTPIN);

		if (counter == 200) {
			break;  // if while breaked by timer, break for
		}

		if ((i >= 4) && (i % 2 == 0)) {
			dht11_dat[j / 8] <<= 1 ;
			if (counter > 20) {
				dht11_dat[j / 8] |= 1;
			}
			j++ ;
		}
	}
	if ((j >= 40) && (dht11_dat[4] == ((dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3]) & 0xff))) {
		printf("humidity = %d.%d %%\n", dht11_dat[0], dht11_dat[1]) ;
	}
	else {
		printf("Data get failed\n");
	}
}

PI_THREAD(sendOpen) {
	while (1) {
		if (dist < 4) {
			openCount++;
		}
	}
}

PI_THREAD(chairCheck) {
	while (1) {
		digitalWrite(TRIG1, LOW);
		digitalWrite(TRIG1, HIGH);

		delayMicroseconds(10);

		digitalWrite(TRIG1, LOW);

		while (digitalRead(ECHO1) == 0) {
			s1 = micros();
		}
		while (digitalRead(ECHO1) == 1) {
			e1 = micros();
		}

		dist1 = (e1 - s1) / 58;

		printf("distance(cm) : %d\n", dist1);

		if(beforeDist != nowDist) {
			sprintf(message3, "chair\t%d\t", dist1);
			
			write(clint_sock, message3, sizeof(message3));
		}

		delay(500);
	}
}

PI_THREAD(openCase) {
	while (1) {
		digitalWrite(TRIG, LOW);
		digitalWrite(TRIG, HIGH);

		delayMicroseconds(10);

		digitalWrite(TRIG, LOW);

		while (digitalRead(ECHO) == 0) {
			s = micros();
		}
		while (digitalRead(ECHO) == 1) {
			e = micros();
		}

		dist = (e - s) / 58;

		printf("distance(cm) : %f\n", dist);

		delay(500);
	}
}

PI_THREAD(sendData) {
	while (1) {
		delay(3000);

		printf("================================================\n");

		sprintf(message1, "temp\t%.0f", temp);

		write(clint_sock, message1, sizeof(message1));

		sprintf(message2, "hum\t%d", dht11_dat[0]);

		write(clint_sock, message2, sizeof(message2));

		if (openCount >= 1) {
			write(clint_sock, openIs, sizeof(openIs));
			openCount = 0;
		}

		delay(60000 - 3000);
	}
}

PI_THREAD(tem) {
	while (1) {
		float tempInput = analogRead(mcp3208, TEMP);

		temp = ((tempInput * 100 * 100) / 1023) / 100;

		printf("%f C\n", temp);

		delay(500);
	}
}

int main(int argc, char* argv[]) {
	if (argc != 2) {
		printf("%s <port>\n", argv[0]);
		exit(1);
	}

	serv_sock = socket(PF_INET, SOCK_STREAM,0);

	if (serv_sock == -1){
		printf("socket error\n");
	}

	memset(&serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_addr.sin_port = htons(atoi(argv[1]));

	if(bind(serv_sock,(struct sockaddr*)&serv_addr, sizeof(serv_addr)) == -1) {
		printf("bind error\n");
	}

	if(listen(serv_sock,5)==-1) {
		printf("listen error\n");
	}

	clnt_addr_size = sizeof(clint_addr);
	clint_sock = accept(serv_sock,(struct sockaddr*)&clint_addr,&clnt_addr_size);

	if(clint_sock == -1) {
		printf("accept error\n");
	}

	wiringPiSetup();

	wiringPiSPISetup(mcp3208.ce, 1000000);

	pinMode(TRIG, OUTPUT);
	pinMode(TRIG1, OUTPUT);
	pinMode(ECHO, INPUT);
	pinMode(ECHO1, INPUT);
	pinMode(DHTPIN, INPUT);

	piThreadCreate(sendOpen);
	piThreadCreate(chairCheck);
	piThreadCreate(openCase);
	piThreadCreate(tem);
	piThreadCreate(sendData);

	while (1) { 
		read_dht11_dat();
		delay(500);
	}
	close(serv_sock);
	close(clint_sock);
}

