#include <winsock2.h>
#include <windows.h>
#include <ws2tcpip.h>
#include <string>
#include <sstream>
#include <iomanip>
#include <iostream>

#include <json.hpp>
#include <stdlib.h>

#define DEFAULT_BUFLEN 51200
#define DEFAULT_PORT "27017"

using json = nlohmann::json;

// compiled using: i686-w64-mingw32-c++ TestEmulatorAGO.cpp -I$(pwd) -lws2_32 -lwsock32 -lpthread -static
// mingw64-winpthreads-static.noarch mingw32-winpthreads-static.noarch mingw32-filesystem mingw64-filesystem mingw32-binutils mingw64-binutils mingw32-gcc mingw64-gcc mingw32-crt mingw64-crt mingw32-headers mingw64-headers wine

std::string get_refresh_data() {
	std::string send_data;

	json j;

	j["PAEncoder"] = "PAEncoder";

	j["PAHAApparent"] = "PAHAApparent";

	j["PAHARAJ2000"] = "PAHARAJ2000";

	j["PAAzimuth"] = 12.23;

	j["PAStatus"] = "PAStatus";

	j["PAFlipped"] = "PAFlipped";

	j["DEEncoder"] = "DEEncoder";

	j["DEApparent"] = "DEApparent";

	j["DEDEJ2000"] = "DEDEJ2000";

	j["DEElevation"] = 23.12;

	j["DEStatus"] = "DEStatus";

	j["DEFlipped"] = "DEFlipped";

	j["DOMEEncoder"] = "DOMEEncoder";

	j["DOMEAzimuth"] = 13.13;

	j["DOMETargetAzimuth"] = "DOMETargetAzimuth";

	j["DOMESynch"] = "DOMESynch";

	j["DOMEStatus"] = "DOMEStatus";

	j["DOMEManOverride"] = "DOMEManOverride";

	j["CAMType"] = "CAMType";

	j["CAMExposure"] = "CAMExposure";

	j["CAMMode"] = "CAMMode";

	j["CAMRBIFlushCount"] = "CAMRBIFlushCount";

	j["CAMRBIFloodTime"] = "CAMRBIFloodTime";

	j["CAMTDIMode"] = "CAMTDIMode";

	j["CAMBGFlush"] = "CAMBGFlush";

	j["CAMBinning"] = "CAMBinning";

	j["CAMSubframe1"] = "CAMSubframe1";

	j["CAMSubframe2"] = "CAMSubframe2";

	j["CAMObserver"] = "CAMObserver";

	j["CAMObject"] = "CAMObject";

	j["CAMNotes"] = "CAMNotes";

	j["CAMSetpoint"] = "CAMSetpoint";

	j["CAMCooler1"] = "CAMCooler1";

	j["CAMCooler2"] = "CAMCooler2";

	j["CAMDelay"] = "CAMDelay";

	j["CAMRemaining1"] = "CAMRemaining1";

	j["CAMRemaining2"] = "CAMRemaining2";

	j["CAMStatus"] = "CAMStatus";


	j["FWFilter"] = "B";

	j["FWStatus"] = "FWStatus";

	j["TAREncoder1"] = "TAREncoder1";

	j["TAREncoder2"] = "TAREncoder2";

	j["TARdEnc1"] = "TARdEnc1";

	j["TARdEnc2"] = "TARdEnc2";

	j["TARHAApparent"] = "TARHAApparent";

	j["TARDEApparent"] = "TARDEApparent";

	j["TARRAJ2000"] = "TARRAJ2000";

	j["TARDEJ2000"] = "TARDEJ2000";

	j["TARAzimuth"] = "TARAzimuth";

	j["TARElevation"] = "TARElevation";

	j["TARPoleCrossing"] = "TARPoleCrossing";

	j["TARStatus"] = "TARStatus";


	j["TIMEUTC"] = "TIMEUTC";

	j["TIMEUT1UTC"] = "TIMEUT1UTC";

	send_data = j.dump(-1, ' ', false, nlohmann::json::error_handler_t::ignore);

	return send_data;
}

std::vector<std::string> split_string(std::string string_to_split, const std::string& delimiter) {
	std::vector<std::string> ret_vector;

	size_t pos = 0;
	std::string token;
	while ((pos = string_to_split.find(delimiter)) != std::string::npos) {
		token = string_to_split.substr(0, pos);
		ret_vector.push_back(std::move(token));
		string_to_split.erase(0, pos + delimiter.length());
	}
	ret_vector.push_back(std::move(string_to_split));

	return ret_vector;
}

int receive_respond(char *recvbuf, char *sendbuf) {
	const std::string rec_data = recvbuf;
	std::string send_data = "";

	std::vector<std::string>tokenized_data = split_string(rec_data, ";");
	int command = stoi(tokenized_data[0]);

	switch (command)
	{
	case(1): {
		break;
	}

	case(306): {
		send_data = (rand() % 1 == 0) ? "Dome moving east" : "Moving dome failed!";
		break;
	}
	case(297): {
		send_data = (rand() % 1 == 0)? "Dome moving west" : "Moving dome failed!";
		break;
	}
	case(295): {
		send_data = (rand() % 1 == 0) ? "Stopping dome" : "Stopping dome failed!";
		break;
	}
	case(102): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set dome frequency! Expected 1 argument! Contact the administrator.";
		}
		else {
			double frequency = stod(tokenized_data[1]);
			send_data = (rand() % 1 == 0) ? "Frequency accepted" : "Failed to set frequency!";
		}
		break;
	}
	case(121): {
		break;
	}
	case(97): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set dome azimuth! Expected 1 argument! Contact the administrator.";
		}
		else {
			double azimuth = stod(tokenized_data[1]);
			send_data = (rand() % 1 == 0) ? "Dome azimuth calibrated" : "Dome azimuth failed to calibrate!";
		}
		break;
	}


	case(71): {
		if ((rand() % 1 == 0)) {
			send_data = (rand() % 1 == 0) ? "Going to target" : "Target not locked, too low in the sky!";
		}
		else {
			send_data = "Target canceled, stopping.";
		}
		break;
	}
	case(99): {
		send_data = (rand() % 1 == 0) ? "Coordinates calibrated" : "Calibration failed!";
		break;
	}
	case(122): {
		send_data = "Coordinates calibrated to zenith!";
		break;
	}
	case(112): {
		break;
	}
	case(76): {
		if (tokenized_data.size() < 3) {
			send_data = "Wrong request to set target coordinates! Expected 2 arguments! Contact the administrator.";
		}
		break;
	}


	case(115): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set setpoint! Expected 1 argument! Contact the administrator.";
		}
		else {
			send_data = (rand() % 1 == 0) ? "Setpoint accepted" : "Setpoint failed!";
		}
		break;
	}
	case(114): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set repeats! Expected 1 argument! Contact the administrator.";
		}
		else {
			int repeats = stoi(tokenized_data[1]);
			send_data = (rand() % 1 == 0) ? "Repeats set!" : "Invalid value for repeats!";
		}
		break;
	}
	case(79): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set observer information! Expected 1 argument! Contact the administrator.";
		}
		else {
			std::string observer = tokenized_data[1];
			if ((rand() % 1 == 0)) {
				send_data = "Entered observer value exceeds the maximum available length!";
			}
		}
		break;
	}
	case(111): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set object information! Expected 1 argument! Contact the administrator.";
		}
		else {
			std::string object = tokenized_data[1];
			if ((rand() % 1 == 0)) {
				send_data = "Entered object value exceeds the maximum available length!";
			}
		}
		break;
	}
	case(110): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set note information! Expected 1 argument! Contact the administrator.";
		}
		else {
			std::string note = tokenized_data[1];
			if ((rand() % 1 == 0)) {
				send_data = "Entered object value exceeds the maximum available length!";
			}
		}
		break;
	}
	case(116): {
		break;
	}
	case(109): {
		break;
	}
	case(101): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set exposure! Expected 1 argument! Contact the administrator.";
		}
		else {
			double exposure = stod(tokenized_data[1]);
			send_data = (rand() % 1 == 0) ? "Exposure accepted" : "Set exposure failed!";
		}
		break;
	}
	case(100): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to set delay! Expected 1 argument! Contact the administrator.";
		}
		else {
			double delay = stod(tokenized_data[1]);
		}
		break;
	}


	case(69): {
		if ((rand() % 1 == 0)) {
			send_data = "Could not expose, camera is not connected!";
		}
		else {
			send_data = "Exposed!";
		}
		break;
	}
	case(88): {
		if ((rand() % 1 == 0)) {
			send_data = "Could not expose, camera is not connected!";
		}
		else {
			send_data = "Exposed!";
		}
		break;
	}
	case(65): {
		break;
	}


	case(72): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to positive declination slewing! Expected 1 argument! Contact the administrator.";
		}
		unsigned int frequency = stoi(tokenized_data[1]);
		if ((rand() % 1 == 0)) {
			send_data = "tel.mcu_de.slew_positive(frequency)";
		}
		break;
	}
	case(80): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to negative declination slewing! Expected 1 argument! Contact the administrator.";
		}
		unsigned int frequency = stoi(tokenized_data[1]);
		if ((rand() % 1 == 0)) {
			send_data = "tel.mcu_de.slew_negative(frequency)";
		}
		break;
	}
	case(77): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to positive right ascension slewing! Expected 1 argument! Contact the administrator.";
		}
		unsigned int frequency = stoi(tokenized_data[1]);
		if ((rand() % 1 == 0)) {
			send_data = "tel.mcu_ra.slew_positive(frequency)";
		}
		break;
	}
	case(75): {
		if (tokenized_data.size() < 2) {
			send_data = "Wrong request to negative right ascension slewing! Expected 1 argument! Contact the administrator.";
		}
		unsigned int frequency = stoi(tokenized_data[1]);
		if ((rand() % 1 == 0)) {
			send_data = "tel.mcu_ra.slew_negative(frequency)";
		}
		break;
	}


	case(84): {
		if ((rand() % 1 == 0)) {
			send_data = "Automatic override finished.";
		}
		else {
			send_data = "This function is available only when the telescope is below certain elevation threshold.";
		}
		break;
	}


	case(87): {
		send_data = "tel.mcu_ra.stop(true)";
		break;
	}
	case(119): {
		send_data = "tel.mcu_de.stop(true)";
		break;
	}


	case(91): {
		send_data = "tel.mcu_ra.enable()";
		send_data.append(" and ");
		send_data.append("tel.mcu_de.enable()");
		break;
	}
	case(93): {
		send_data = "tel.mcu_ra.disable()";
		send_data.append(" and ");
		send_data.append("tel.mcu_de.disable()");
		break;
	}


	case(59): {
		break;
	}
	case(46): {
		break;
	}


	case(67): {
		break;
	}
	case(66): {
		break;
	}
	case(86): {
		break;
	}
	case(82): {
		break;
	}
	case(73): {
		break;

	}


	case(68): {
		if ((rand() % 1 == 0)) {
			send_data = "Dumping stopped";
		}
		else {
			send_data = "Dumping ...";
		}
		break;
	}


	}

	strcpy(sendbuf, send_data.c_str());
	return send_data.length();
}

DWORD WINAPI refreshLoop(LPVOID lpParam) {
	auto clientSocket = (SOCKET*)lpParam;
	int iSendResult;
	std::string send_data;

	while (true) {
		send_data = get_refresh_data();
		iSendResult = send(*clientSocket, send_data.c_str(), send_data.length()+1, 0);
		if (iSendResult == SOCKET_ERROR) {
			return 1;
		}
		Sleep(200);
	}
	return 0;
}

int main()
{
	WSADATA wsaData;
	int iResult;

	SOCKET ListenSocket = INVALID_SOCKET;
	SOCKET ClientSocket = INVALID_SOCKET;

	struct addrinfo *result = NULL;
	struct addrinfo hints;

	sockaddr caddr;
	int addrlen = 256;
	int sendbuflen = DEFAULT_BUFLEN;

	int iSendResult;
	char * recvbuf = new char[DEFAULT_BUFLEN];
	char * sendbuf = new char[DEFAULT_BUFLEN];
	int buflen = DEFAULT_BUFLEN;

	// Initialize Winsock
	iResult = WSAStartup(MAKEWORD(2, 2), &wsaData);
	if (iResult != 0) {
		printf("WSAStartup failed with error: %d\n", iResult);
		return 1;
	}

	ZeroMemory(&hints, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_protocol = IPPROTO_TCP;
	hints.ai_flags = AI_PASSIVE;

	// Resolve the server address and port
	iResult = getaddrinfo("localhost", DEFAULT_PORT, &hints, &result);
	if (iResult != 0) {
		printf("getaddrinfo failed with error: %d\n", iResult);
		delete[] recvbuf;
		delete[] sendbuf;
		WSACleanup();
		return 1;
	}

	// Create a SOCKET for connecting to server
	ListenSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
	if (ListenSocket == INVALID_SOCKET) {
		printf("socket failed with error: %ld\n", WSAGetLastError());
		freeaddrinfo(result);
		delete[] recvbuf;
		delete[] sendbuf;
		WSACleanup();
		return 1;
	}

	// Setup the TCP listening socket
	iResult = ::bind(ListenSocket, result->ai_addr, (int)result->ai_addrlen);
	if (iResult == SOCKET_ERROR) {
		char debugstring[256];
		sprintf(debugstring, "bind failed with error: %d\n", WSAGetLastError());
		//printf("bind failed with error: %d\n", WSAGetLastError());
		freeaddrinfo(result);
		closesocket(ListenSocket);
		delete[] recvbuf;
		delete[] sendbuf;
		WSACleanup();
		return 1;
	}

	freeaddrinfo(result);

	iResult = listen(ListenSocket, SOMAXCONN);
	if (iResult == SOCKET_ERROR) {
		printf("listen failed with error: %d\n", WSAGetLastError());
		closesocket(ListenSocket);
		delete[] recvbuf;
		delete[] sendbuf;
		WSACleanup();
		return 1;
	}

	// Accept a client socket
	ClientSocket = accept(ListenSocket, &caddr, &addrlen);
	if (ClientSocket == INVALID_SOCKET) {
		closesocket(ListenSocket);
		delete[] recvbuf;
		delete[] sendbuf;
		WSACleanup();
		return 1;
	}

	// No longer need server socket
	closesocket(ListenSocket);

	//tel.update();
	//readState();

	DWORD dwThreadRefresh = NULL;
	HANDLE hThreadRefresh = CreateThread(0,
		0,
		refreshLoop,
		&ClientSocket,
		0,
		&dwThreadRefresh);

	// Receive until the peer shuts down the connection
	do {
		iResult = recv(ClientSocket, recvbuf, buflen, 0);
		if (iResult > 0) {
			printf("Bytes received: %d\n", iResult);
			printf("Message received: %s\n", recvbuf);

			sendbuflen = receive_respond(recvbuf, sendbuf);
			printf("Sendbuflen %d\n", sendbuflen);

			iSendResult = send(ClientSocket, sendbuf, sendbuflen+1, 0);

			if (iSendResult == SOCKET_ERROR) {
				printf("send failed with error: %d\n", WSAGetLastError());
				closesocket(ClientSocket);
				WSACleanup();
			}
			printf("Bytes sent: %d\n", iSendResult);
		}
		else if (iResult == 0)
			printf("Connection closing...\n");
		else {
			printf("recv failed with error: %d\n", WSAGetLastError());
			closesocket(ClientSocket);
			WSACleanup();
		}

	} while (iResult > 0);

	// shutdown the connection since we're done
	iResult = shutdown(ClientSocket, SD_SEND);
	if (iResult == SOCKET_ERROR) {
		printf("shutdown failed with error: %d\n", WSAGetLastError());
		closesocket(ClientSocket);
		WSACleanup();
	}

	// cleanup
	closesocket(ClientSocket);
	WSACleanup();
	delete[] recvbuf;
	delete[] sendbuf;
	if (hThreadRefresh != NULL) {
		if (TerminateThread(hThreadRefresh, NULL) != 0) {
			hThreadRefresh = NULL;
		}
	}
    return 0;
}

