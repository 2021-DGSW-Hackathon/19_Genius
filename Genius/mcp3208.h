#ifndef __MCP3208_H__
#define __MCP3208_H__

#include<stdio.h>
#include<stdint.h>
#include<wiringPi.h>
#include<wiringPiSPI.h>

#define CEO 0

typedef struct {
	uint32_t ce;
} mcp3208_t;

int32_t analogRead(mcp3208_t& mcp3208, uint32_t channel);

#endif
