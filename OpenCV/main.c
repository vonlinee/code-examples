#include <stdio.h>
#include <string.h>
#include <time.h>
#include <windows.h>
#include <mmsystem.h>

int main(char *argc, char** argv) {
    SYSTEMTIME sys;
    GetLocalTime(&sys);
    char* t_y = malloc(128 * sizeof(char));

    sprintf(t_y, ("%4.4d-%2.2d-%2.2d %2.2d:%2.2d:%2.2d"),
    sys.wYear, sys.wMonth, sys.wDay,
    sys.wHour, sys.wMinute, sys.wSecond);
    return 1;
}