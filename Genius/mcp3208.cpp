#include"mcp3208.h"

int32_t analogRead(mcp3208_t& mcp3208, uint32_t channel) {
	uint8_t buf[3];
	int32_t abcValue = 0;

	buf[0] = (1 << 2) | (1 << 1) | ((channel & 4) >> 2);
	buf[1] = (channel & 3) << 6;
	buf[2] = 0;

	wiringPiSPIDataRW(mcp3208.ce, buf, 3);

	abcValue = ((buf[1] & 0xF) << 8) | buf[2];

	return abcValue;
}
