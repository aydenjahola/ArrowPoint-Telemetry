REM Green On Solid
call USBCMDAP V 0 0 101 12 1 0 

REM All Previous Status Off

REM Green Off
REM USBCMDAP V 0 0 101 12  0 1
REM USBCMDAP V 0 0 101 20  1 0

REM Orange Off
USBCMDAP V 0 0 101 12  0 4
USBCMDAP V 0 0 101 20  4 0

REM Red Off
USBCMDAP V 0 0 101 12  0 2
USBCMDAP V 0 0 101 20  2 0

REM Orange Off
USBCMDAP V 0 0 101 12  0 4
USBCMDAP V 0 0 101 20  4 0

REM Red Off
USBCMDAP V 0 0 101 12  0 2
USBCMDAP V 0 0 101 20  2 0
