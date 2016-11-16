#ifndef GODYTICS_H
#define GODYTICS_H

#include "reference.h"

#ifdef __OBJC__
#include "GAI.h"
#include "GAIDictionaryBuilder.h"
#include "GAIFields.h"
#endif

class Godytics : public Reference {
    OBJ_TYPE(Godytics,Reference);

    bool initialized;

    #ifdef __OBJC__
    id<GAITracker> tracker;
    #endif

protected:
    static void _bind_methods();

public:

    void init(const String &tId);
    void screen(const String &name);
    void event(const String &cat, const String &act, const String &lab);

    Godytics();
    ~Godytics();
};

#endif
