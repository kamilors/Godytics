#include "register_types.h"
#include "object_type_db.h"
#include "core/globals.h"
#include "ios/src/godytics.h"

void register_godytics_types() {
    Globals::get_singleton()->add_singleton(Globals::Singleton("Godytics", memnew(Godytics)));
}

void unregister_godytics_types() {
}
