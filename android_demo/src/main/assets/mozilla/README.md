# Mozilla Example APNG files

Source: https://people.mozilla.org/~dolske/apng/demo.html

``demo-1.png``: An APNG file showing that the IDAT (default image) is not part
of the animation. The default image has text that reads "Your browser does NOT 
support Animated PNGs" and will be shown in non-APNG-aware viewers. The second
frame reads "Your browser DOES support Animated PNGs" and will only show in
APNG-aware viewers. Nifty.

``demo-2-over+none.png``: The 'dispose' field frame control chunk (fcTL) of every frame is set
to "0" meaning the contents of the prior frame are left as-is when the new frame
is blended. The blend operation on each frame is "1" meaning "blend over" using
alpha compositing.

See: https://wiki.mozilla.org/APNG_Specification#.60fcTL.60:_The_Frame_Control_Chunk

``demo-2-over+background.png``:  As above except the 'dispose' field of each frame 
control chunk is set to "1" meaning "clear the frame to fully transparent black before
rendering the frame".

``demo-2-over+previous.png``: As for "none" except the 'dispose' field of each
frame control chunk is set to "2" meaning "the frame's region of the output buffer
 is to be reverted to the previous contents before rendering the next frame"

``loading_16.png``: A 12 frame animation that repeats forever. Each frame is 16x16
truecolour with alpha (4 x 1 byte = 8 bytes per pixel). The 'dispose' field of each
frame is set to "1" so the frame is completely cleared and no blending is required.


