TARGET_EXEC := server

CC := gcc
CFLAGS := -g -Wall -Wextra -pthread 
LDFLAGS := 

SRC_DIR := src
OBJ_DIR := obj
INC_DIR := includes

SRCS := $(wildcard $(SRC_DIR)/*.c)
OBJS := $(patsubst %.c,%.o,$(subst src,obj,$(SRCS)))

INC_FLAGS := $(addprefix -I,$(INC_DIR))

all: $(TARGET_EXEC) 

$(TARGET_EXEC): $(OBJS)
	$(CC) $(OBJS) -o $@ $(LDFLAGS)

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c 
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(OBJS): | $(OBJ_DIR)

$(OBJ_DIR):
	@echo "Making objects dir..."
	mkdir -p $(OBJ_DIR)

.PHONY: clean

clean:
	@echo "Cleaning..."
	rm -r $(OBJ_DIR) $(TARGET_EXEC)
