# First maze:
robot initial position:

    robot->x = 40;
    robot->y = 410;
    robot->true_x = 40;
    robot->true_y = 410;

Maze code:

    insertAndSetFirstWall(&head, 1, 15, 25, 10, 430);
    insertAndSetFirstWall(&head, 1, 75, 145, 10, 60);
    insertAndSetFirstWall(&head, 1, 75, 325, 10, 120);
    insertAndSetFirstWall(&head, 1, 135, 85, 10, 60);
    insertAndSetFirstWall(&head, 1, 135, 325, 10, 60);
    insertAndSetFirstWall(&head, 1, 195, 145, 10, 300);
    insertAndSetFirstWall(&head, 1, 255, 145, 10, 120);
    insertAndSetFirstWall(&head, 1, 315, 25, 10, 60);
    insertAndSetFirstWall(&head, 1, 315, 265, 10, 130);
    insertAndSetFirstWall(&head, 1, 375, 85, 10, 70);
    insertAndSetFirstWall(&head, 1, 375, 265, 10, 120);
    insertAndSetFirstWall(&head, 1, 435, 85, 10, 60);
    insertAndSetFirstWall(&head, 1, 435, 325, 10, 130);
    insertAndSetFirstWall(&head, 1, 495, 145, 10, 180);
    insertAndSetFirstWall(&head, 1, 555, 25, 10, 130);
    insertAndSetFirstWall(&head, 1, 555, 205, 10, 190);
    insertAndSetFirstWall(&head, 1, 615, 25, 10, 430);

    insertAndSetFirstWall(&head, 1, 15, 25, 540, 10);
    insertAndSetFirstWall(&head, 1, 75, 85, 190, 10);
    insertAndSetFirstWall(&head, 1, 435, 85, 60, 10);
    insertAndSetFirstWall(&head, 1, 315, 145, 60, 10);
    insertAndSetFirstWall(&head, 1, 495, 145, 60, 10);
    insertAndSetFirstWall(&head, 1, 75, 205, 60, 10);
    insertAndSetFirstWall(&head, 1, 315, 205, 180, 10);
    insertAndSetFirstWall(&head, 1, 15, 265, 120, 10);
    insertAndSetFirstWall(&head, 1, 255, 265, 60, 10);
    insertAndSetFirstWall(&head, 1, 375, 265, 60, 10);
    insertAndSetFirstWall(&head, 1, 195, 325, 60, 10);
    insertAndSetFirstWall(&head, 1, 255, 385, 60, 10);
    insertAndSetFirstWall(&head, 1, 495, 385, 60, 10);
    insertAndSetFirstWall(&head, 1, 75, 445, 540, 10);


# Second maze:
robot initial position:

    robot->x = 125;
    robot->y = 445;
    robot->true_x = 125;
    robot->true_y = 445;


Maze code:

    int i = 0;
    insertAndSetFirstWall(head_ptr, 1, 90, 400, 10, 80);
    insertAndSetFirstWall(head_ptr, 2, 170, 400, 10, 80);
    insertAndSetFirstWall(head_ptr, 3, 10, 400, 80, 10);
    insertAndSetFirstWall(head_ptr, 4, 170, 400, 80, 10);
    insertAndSetFirstWall(head_ptr, 5, 10, 320, 10, 80);
    insertAndSetFirstWall(head_ptr, 6, 10, 320, 240, 10);
    for (i=0; i<20; i++) { insertAndSetFirstWall(head_ptr, 7, 245+i*1, 320-i*1, 10, 8); }
    for (i=0; i<20; i++) { insertAndSetFirstWall(head_ptr, 8, 245+i*1, 400+i*1, 10, 8); }
    insertAndSetFirstWall(head_ptr, 9, 265, 420, 180, 10);
    insertAndSetFirstWall(head_ptr, 10, 100, 300, 360, 10);
    insertAndSetFirstWall(head_ptr, 11, 445, 420, 10, 50);
    insertAndSetFirstWall(head_ptr, 12, 445, 470, 185, 10);
    insertAndSetFirstWall(head_ptr, 15, 620, 300, 10, 170);
    insertAndSetFirstWall(head_ptr, 16, 540, 300, 80, 10);
    for (i=0; i<80; i++) { insertAndSetFirstWall(head_ptr, 17, 460-i*0.5, 300-i*1, 10, 8); }
    for (i=0; i<50; i++) { insertAndSetFirstWall(head_ptr, 18, 540-i*0.5, 300-i*1, 10, 8); }
    for (i=0; i<50; i++) { insertAndSetFirstWall(head_ptr, 19, 420+i*1.8, 220-i*1, 10, 8); }
    for (i=0; i<70; i++) { insertAndSetFirstWall(head_ptr, 20, 515+i*1.5, 250-i*1, 10, 8); }
    for (i=0; i<90; i++) { insertAndSetFirstWall(head_ptr, 21, 510-i*1.5, 170-i*1, 10, 8); }
    for (i=0; i<160; i++) { insertAndSetFirstWall(head_ptr, 22, 620-i*0.5, 180-i*1, 10, 8); }
    insertAndSetFirstWall(head_ptr, 24, 320, 20, 220, 10);
    float radius = 60; // Set radius here
    for (float i = M_PI; i < 2 * M_PI; i += 0.01) {
    float x = radius * cos(i);
    float y = radius * sin(i);
    //rotate + coordinate
    float x_rotated = y+320;
    float y_rotated = -x+80;
    insertAndSetFirstWall(head_ptr, 25, x_rotated, y_rotated, 10, 10); }
    insertAndSetFirstWall(head_ptr, 26, 320, 140, 40, 10);
    insertAndSetFirstWall(head_ptr, 27, 370, 210, 10, 90);
    insertAndSetFirstWall(head_ptr, 28, 290, 140, 10, 100);
    insertAndSetFirstWall(head_ptr, 29, 240, 240, 60, 10);
    insertAndSetFirstWall(head_ptr, 30, 240, 180, 10, 60);
    insertAndSetFirstWall(head_ptr, 31, 180, 180, 10, 60);
    insertAndSetFirstWall(head_ptr, 31, 180, 180, 60, 10);

    float radius2 = 40; // Set radius here
    for (float i = M_PI; i < 2 * M_PI; i += 0.01) {
    float x = radius2 * cos(i);
    float y = radius2 * sin(i+10);
    //rotate + coordinate
    float x_rotated = -y+160;
    float y_rotated = x+200;
    insertAndSetFirstWall(head_ptr, 25, x_rotated, y_rotated, 10, 10); }

    float radius3 = 70; // Set radius here
    for (float i = M_PI; i < 2 * M_PI; i += 0.01) {
    float x = radius3 * cos(i);
    float y = radius3 * sin(i+10);
    //rotate + coordinate
    float x_rotated = -y+80;
    float y_rotated = x+230;
    insertAndSetFirstWall(head_ptr, 25, x_rotated, y_rotated, 10, 10); }

    for (float i = M_PI; i < 2 * M_PI; i += 0.01) {
    float x = radius3 * cos(i);
    float y = radius3 * sin(i+10);
    //rotate + coordinate
    float x_rotated = y+110;
    float y_rotated = -x+90;
    insertAndSetFirstWall(head_ptr, 25, x_rotated, y_rotated, 10, 10); }

    for (float i = M_PI; i < 2 * M_PI; i += 0.01) {
    float x = radius3 * cos(i);
    float y = radius3 * sin(i+10);
    //rotate + coordinate
    float x_rotated = y+0;
    float y_rotated = -x+90;
    insertAndSetFirstWall(head_ptr, 25, x_rotated-10, y_rotated, 10, 10); }
    insertAndSetFirstWall(head_ptr, 31, 0, 0, 10, 50);
    insertAndSetFirstWall(head_ptr, 31, 70, 0, 10, 30);


# Third maze:
robot initail position:
    robot->x = 10;
    robot->y = 200;
    robot->true_x = 10;
    robot->true_y = 200;
    robot->width = ROBOT_WIDTH;
    robot->height = ROBOT_HEIGHT;
    robot->direction = 0;
    robot->angle = 90;
    robot->currentSpeed = 0;
    robot->crashed = 0;
    robot->auto_mode = 0;


maze code:

insertAndSetFirstWall(&head, name_index++,  0, 0, 10, 130);

    insertAndSetFirstWall(&head, name_index++,  80, 0, 10, 30);

    insertAndSetFirstWall(&head, name_index++,  80, 30, 30, 10);

    insertAndSetFirstWall(&head, name_index++,  0, 130, 110, 10);




   int i, a, b, c, d, e, f, g, h, k, l, m;

    double j;

 a = 30;

    b = 30;

    c = 10;

 d = 3;

 e = 110;

 f = 130;

 g = b;

 h = c;

 k = d;

 l = e;

 m = 90;

    for (i = 0; i < m; i++){

        j = i;

        insertAndSetFirstWall(&head, name_index++,

                              (i * d)+e,

                              a + b*sin(c*j * M_PI/180),

                              10, 10);

        insertAndSetFirstWall(&head, name_index++,

                              (i * k)+l,

                              f + g*sin(h*j * M_PI/180),

                              10, 10);

    }

    for (i = 0; i < 100; i++){

        j = i;

        insertAndSetFirstWall(&head, name_index++,

                              // the most important bit is below.

                              // increase the 20 for a tighter bend

                              // descrease for a more meandering flow

                              380 + 130*sin(1.8*j * M_PI/180),

                              // increase the 5 for a spacier curve

                              (i * 2)+130,

                              10, 10);

        insertAndSetFirstWall(&head, name_index++,

                              // the most important bit is below.

                              // increase the 20 for a tighter bend

                              // descrease for a more meandering flow

                              380 + 230*sin(1.8*j * M_PI/180),

                              // increase the 5 for a spacier curve

                              (i * 4)+30,

                              10, 10);

    }




    float aa, bb;

    a = 200;

    aa = 0.5;

    bb = 1;

    c = 180;

    d = 1;

    e = a+100;

    f = c;

    m = 250;

    for (i = 100; i < m; i++){

        insertAndSetFirstWall(&head, name_index++,  a + i*aa , c + i*bb, 10, 10);

    }

    insertAndSetFirstWall(&head, name_index++,  330, 430, 60, 10);

    m = 150;

    for (i = 0; i < m; i++){

        insertAndSetFirstWall(&head, name_index++,  e + i*aa , f + i*bb, 10, 10);

    }

    insertAndSetFirstWall(&head, name_index++,  150, 180, 150, 10);

    e = a-50;

    aa = 0.25;

    for (i = 0; i < m; i++){

        insertAndSetFirstWall(&head, name_index++,  e - i*aa , f + i*bb, 10, 10);

    }

    a=a+50;

    c=c+100;

    aa = 0.75;

    for (i = 0; i < m; i++){

        insertAndSetFirstWall(&head, name_index++,  a - i*aa , c + i*bb, 10, 10);

    }

    insertAndSetFirstWall(&head, name_index++,  40, 430, 100, 10);

    insertAndSetFirstWall(&head, name_index++,  40, 240, 10, 190);

    insertAndSetFirstWall(&head, name_index++,  110, 180, 10, 160);

    insertAndSetFirstWall(&head, name_index++,  0, 240, 40, 10);

    insertAndSetFirstWall(&head, name_index++,  0, 180, 110, 10);

